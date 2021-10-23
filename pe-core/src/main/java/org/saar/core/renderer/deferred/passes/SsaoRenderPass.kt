package org.saar.core.renderer.deferred.passes

import org.joml.Math
import org.joml.Vector3f
import org.saar.core.painting.painters.Random2fPainter
import org.saar.core.painting.renderToTexture
import org.saar.core.postprocessing.processors.GaussianBlurPostProcessor
import org.saar.core.postprocessing.processors.MultiplyPostProcessor
import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.ScreenPrototype
import org.saar.core.screen.Screens
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ColourScreenImage
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.constants.ColourFormatType
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.parameter.*
import org.saar.lwjgl.opengl.texture.values.MagFilterValue
import org.saar.lwjgl.opengl.texture.values.MinFilterValue
import org.saar.lwjgl.opengl.texture.values.WrapValue
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Vector2
import org.saar.maths.utils.Vector3
import kotlin.random.Random

class SsaoRenderPass(val radius: Float = 10f) : DeferredRenderPass {

    private val noiseTextureSize = 64
    private val kernelSamplesSize = 32

    private val ssaoPrototype = SsaoRenderPassPrototype(
        createNoiseTexture(), createKernel(), this.noiseTextureSize, radius)
    private val ssaoWrapper = RenderPassPrototypeWrapper(this.ssaoPrototype)

    private val ssaoTexture = MutableTexture2D.create()
    private val screen = Screens.fromPrototype(object : ScreenPrototype {
        @ScreenImageProperty(draw = true, read = true)
        private val colourImage = ColourScreenImage(ColourAttachment
            .withTexture(0, ssaoTexture, ColourFormatType.R16F))
    }, Fbo.create(0, 0))

    private val blurPostProcessor = GaussianBlurPostProcessor(11, 2)
    private val multiplyPostProcessor = MultiplyPostProcessor(ssaoTexture, 1)

    private fun createNoiseTexture(): MutableTexture2D {
        val painter = Random2fPainter()
        val texture = painter.renderToTexture(
            this.noiseTextureSize, this.noiseTextureSize,
            ColourFormatType.RG16F)
        painter.delete()

        texture.applyParameters(arrayOf<TextureParameter>(
            TextureMinFilterParameter(MinFilterValue.NEAREST),
            TextureMagFilterParameter(MagFilterValue.NEAREST),
            TextureSWrapParameter(WrapValue.REPEAT),
            TextureTWrapParameter(WrapValue.REPEAT)
        ))

        return texture
    }

    private fun createKernel() = Array(this.kernelSamplesSize) { i ->
        val x = Random.nextFloat() * 2 - 1
        val y = Random.nextFloat() * 2 - 1
        val z = Random.nextFloat()

        val scale = Math.lerp(.1f, 1f, (i / 64f) * (i / 64f))

        Vector3.of(x, y, z).normalize(Random.nextFloat() * scale)
    }

    override fun prepare(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        this.screen.setAsDraw()
        this.screen.assureSize(
            Window.current().width,
            Window.current().height
        )

        this.ssaoWrapper.render {
            this.ssaoPrototype.normalSpecularTexture.value = buffers.normalSpecular
            this.ssaoPrototype.depthTextureUniform.value = buffers.depth

            this.ssaoPrototype.projectionMatrixInvUniform.value =
                context.camera.projection.matrix.invertPerspective(Matrix4.temp)

            this.ssaoPrototype.projectionMatrixUniform.value.set(context.camera.projection.matrix)
        }
    }

    override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        this.multiplyPostProcessor.render(context, buffers)
    }

    override fun delete() {
        this.ssaoWrapper.delete()
        this.ssaoPrototype.noiseTexture.delete()
        this.blurPostProcessor.delete()
        this.multiplyPostProcessor.delete()
        this.screen.delete()
    }
}

private class SsaoRenderPassPrototype(val noiseTexture: MutableTexture2D,
                                      val kernel: Array<Vector3f>,
                                      val noiseTextureSize: Int,
                                      val radius: Float) : RenderPassPrototype {

    @UniformProperty
    val normalSpecularTexture = TextureUniformValue("u_normalSpecularTexture", 0)

    @UniformProperty
    val depthTextureUniform = TextureUniformValue("u_depthTexture", 1)

    @UniformProperty
    private val noiseTextureUniform = object : TextureUniform() {
        override val name = "u_noiseTexture"

        override val value get() = noiseTexture

        override val unit = 2
    }

    @UniformProperty
    private val kernelUniform = UniformArray("u_kernel", this.kernel.size) { name, index ->
        Vec3UniformValue(name, kernel[index])
    }

    @UniformProperty
    val noiseScaleUniform = object : Vec2Uniform() {
        override val name = "u_noiseScale"

        override val value
            get() = Vector2.of(
                Window.current().width.toFloat(),
                Window.current().height.toFloat()
            ).div(noiseTextureSize.toFloat())
    }

    @UniformProperty
    val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    val projectionMatrixUniform = Mat4UniformValue("u_projectionMatrix")

    @UniformProperty
    val radiusUniform = object : FloatUniform() {
        override val name = "u_radius"

        override val value get() = radius
    }

    override val fragmentShader: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("KERNEL_SAMPLES", kernel.size.toString()),
        ShaderCode.loadSource("/shaders/deferred/ssao/ssao.fragment.glsl"))
}

/*
private class SsaoLightRenderPassPrototype() : RenderPassPrototype {

    @UniformProperty
    val colourTextureUniform = TextureUniformValue("u_colourTexture", 1)

    @UniformProperty
    val normalSpecularTexture = TextureUniformValue("u_normalSpecularTexture", 2)

    @UniformProperty
    val depthTextureUniform = TextureUniformValue("u_depthTexture", 3)

    @UniformProperty
    val ssaoTextureUniform = TextureUniformValue("u_ssaoTexture", 4)

    @UniformProperty
    val lightUniform = DirectionalLightUniformValue("u_light")

    @UniformProperty
    val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/deferred/ssao/light.fragment.glsl"))
}*/

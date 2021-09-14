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
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.textures.Texture
import org.saar.lwjgl.opengl.textures.TextureTarget
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter
import org.saar.lwjgl.opengl.textures.parameters.MinFilterParameter
import org.saar.lwjgl.opengl.textures.parameters.WrapParameter
import org.saar.lwjgl.opengl.textures.settings.TextureMagFilterSetting
import org.saar.lwjgl.opengl.textures.settings.TextureMinFilterSetting
import org.saar.lwjgl.opengl.textures.settings.TextureSWrapSetting
import org.saar.lwjgl.opengl.textures.settings.TextureTWrapSetting
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

    private val ssaoTexture = Texture.create(TextureTarget.TEXTURE_2D)
    private val screen = Screens.fromPrototype(object : ScreenPrototype {
        @ScreenImageProperty
        private val colourImage = ColourScreenImage(ColourAttachment.withTexture(
            0, ssaoTexture, ColourFormatType.R16F, FormatType.RED, DataType.FLOAT))
    }, Fbo.create(0, 0))

    private val blurPostProcessor = GaussianBlurPostProcessor(11, 2)
    private val multiplyPostProcessor = MultiplyPostProcessor(ssaoTexture, 1)

    private fun createNoiseTexture(): Texture {
        val painter = Random2fPainter()
        val texture = painter.renderToTexture(
            this.noiseTextureSize, this.noiseTextureSize,
            ColourFormatType.RG16F, FormatType.RG, DataType.FLOAT)
        painter.delete()

        texture.setSettings(TextureTarget.TEXTURE_2D,
            TextureMinFilterSetting(MinFilterParameter.NEAREST),
            TextureMagFilterSetting(MagFilterParameter.NEAREST),
            TextureSWrapSetting(WrapParameter.REPEAT),
            TextureTWrapSetting(WrapParameter.REPEAT))

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
        this.screen.resizeToMainScreen()
        this.screen.setAsDraw()

        this.ssaoWrapper.render {
            this.ssaoPrototype.normalTextureUniform.value = buffers.normal
            this.ssaoPrototype.depthTextureUniform.value = buffers.depth

            this.ssaoPrototype.projectionMatrixInvUniform.value =
                context.camera.projection.matrix.invertPerspective(Matrix4.temp)

            this.ssaoPrototype.projectionMatrixUniform.value = context.camera.projection.matrix
        }
    }

    override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        this.multiplyPostProcessor.render(context, buffers)
    }

    override fun delete() {
        this.ssaoWrapper.delete()
        this.ssaoPrototype.noiseTexture.delete()
    }
}

private class SsaoRenderPassPrototype(val noiseTexture: Texture,
                                      val kernel: Array<Vector3f>,
                                      val noiseTextureSize: Int,
                                      val radius: Float) : RenderPassPrototype {

    @UniformProperty
    val normalTextureUniform = TextureUniformValue("u_normalTexture", 0)

    @UniformProperty
    val depthTextureUniform = TextureUniformValue("u_depthTexture", 1)

    @UniformProperty
    private val noiseTextureUniform = object : TextureUniform() {
        override fun getUniformValue() = noiseTexture

        override fun getName() = "u_noiseTexture"

        override fun getUnit() = 2
    }

    @UniformProperty
    private val kernelUniform = UniformArray("u_kernel", this.kernel.size) { name, index ->
        object : Vec3Uniform() {
            override fun getUniformValue() = kernel[index]

            override fun getName() = name
        }
    }

    @UniformProperty
    val noiseScaleUniform = object : Vec2Uniform() {
        override fun getName() = "u_noiseScale"

        override fun getUniformValue() = Vector2.of(
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
        override fun getName() = "u_radius"

        override fun getUniformValue() = radius
    }

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("KERNEL_SAMPLES", kernel.size.toString()),
        ShaderCode.loadSource("/shaders/deferred/ssao/ssao.fragment.glsl"))
}

/*
private class SsaoLightRenderPassPrototype() : RenderPassPrototype {

    @UniformProperty
    val colourTextureUniform = TextureUniformValue("u_normalTexture", 1)

    @UniformProperty
    val normalTextureUniform = TextureUniformValue("u_normalTexture", 2)

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

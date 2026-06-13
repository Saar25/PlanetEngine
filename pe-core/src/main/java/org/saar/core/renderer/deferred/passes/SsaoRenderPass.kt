package org.saar.core.renderer.deferred.passes

import org.joml.Math
import org.joml.Vector2f
import org.joml.Vector3f
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.painting.Random2fPainter
import org.saar.core.postprocessing.GaussianBlurPostProcessor
import org.saar.core.postprocessing.MultiplyPostProcessor
import org.saar.core.renderer.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.core.screen.ScreenBuilder
import org.saar.core.screen.assureSize
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.*
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.lwjgl.opengl.texture.parameter.TextureMagFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureMinFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureSWrapParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureTWrapParameter
import org.saar.lwjgl.opengl.texture.values.MagFilterValue
import org.saar.lwjgl.opengl.texture.values.MinFilterValue
import org.saar.lwjgl.opengl.texture.values.WrapValue
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Vector3
import kotlin.random.Random

class SsaoRenderPass(
    private val albedoBuffer: ReadOnlyTexture2D,
    private val normalSpecularBuffer: ReadOnlyTexture2D,
    private val depthBuffer: ReadOnlyTexture2D,
    val radius: Float = 1f
) : RenderNode {

    private val noiseTextureSize = 64
    private val kernelSamplesSize = 32

    private val ssaoPrototype = SsaoRenderPassPrototype(
        createNoiseTexture(), createKernel(), this.noiseTextureSize, this.radius
    )
    private val ssaoWrapper = RendererPrototypeHelper(this.ssaoPrototype)

    private val ssaoTexture = MutableTexture2D.create()
    private val screen = ScreenBuilder(Fbo.create(0, 0))
        .addColorTexture(this.ssaoTexture, InternalFormat.R16F).setRead()
        .build()

    private val blurPostProcessor = GaussianBlurPostProcessor(this.ssaoTexture, 11, 2)

    private val multiplyPostProcessor = MultiplyPostProcessor(this.albedoBuffer, this.ssaoTexture, 1)

    private fun createNoiseTexture(): MutableTexture2D {
        val texture = Random2fPainter().let { painter ->
            Renderers.renderToTexture(this.noiseTextureSize, this.noiseTextureSize, InternalFormat.RG16F) {
                painter.render(RenderContext(null))
            }.also { painter.delete() }
        }.apply {
            applyParameters(
                TextureMinFilterParameter(MinFilterValue.NEAREST),
                TextureMagFilterParameter(MagFilterValue.NEAREST),
                TextureSWrapParameter(WrapValue.REPEAT),
                TextureTWrapParameter(WrapValue.REPEAT)
            )
        }

        return texture
    }

    private fun createKernel() = Array(this.kernelSamplesSize) { i ->
        val x = Random.nextFloat() * 2 - 1
        val y = Random.nextFloat() * 2 - 1
        val z = Random.nextFloat()

        val scale = Math.lerp(.1f, 1f, (i / 64f) * (i / 64f))

        Vector3.of(x, y, z).normalize(Random.nextFloat() * scale)
    }

    override fun render(context: RenderContext) {
        this.screen.setAsDraw()
        this.screen.assureSize(
            Window.current().width,
            Window.current().height
        )

        this.ssaoWrapper.render(context) {
            this.ssaoPrototype.normalSpecularTexture.value = this.normalSpecularBuffer
            this.ssaoPrototype.depthTextureUniform.value = this.depthBuffer

            this.ssaoPrototype.projectionMatrixInvUniform.value =
                context.camera.projection.matrix.invertPerspective(Matrix4.temp)

            this.ssaoPrototype.projectionMatrixUniform.value.set(context.camera.projection.matrix)
        }

        this.multiplyPostProcessor.render(context)
    }

    override fun delete() {
        this.ssaoWrapper.delete()
        this.ssaoPrototype.noiseTexture.delete()
        this.blurPostProcessor.delete()
        this.multiplyPostProcessor.delete()
        this.screen.delete()
    }
}

private class SsaoRenderPassPrototype(
    val noiseTexture: MutableTexture2D,
    val kernel: Array<Vector3f>,
    val noiseTextureSize: Int,
    val radius: Float,
) : RendererPrototype<Unit> {

    @UniformProperty
    val normalSpecularTexture = TextureUniformValue("u_normalSpecularTexture", 0)

    @UniformProperty
    val depthTextureUniform = TextureUniformValue("u_depthTexture", 1)

    @UniformProperty
    private val noiseTextureUniform = object : TextureUniform() {
        override val name = "u_noiseTexture"

        override val value get() = this@SsaoRenderPassPrototype.noiseTexture

        override val unit = 2
    }

    @UniformProperty
    private val kernelUniform = UniformArray("u_kernel", this.kernel.size) { name, index ->
        Vec3UniformValue(name, this.kernel[index])
    }

    @UniformProperty
    val noiseScaleUniform = object : Vec2Uniform() {
        override val name = "u_noiseScale"

        override val value = Vector2f()
            get() = field.set(
                MainScreen.width.toFloat(),
                MainScreen.height.toFloat()
            ).div(this@SsaoRenderPassPrototype.noiseTextureSize.toFloat())
    }

    @UniformProperty
    val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    val projectionMatrixUniform = Mat4UniformValue("u_projectionMatrix")

    @UniformProperty
    val radiusUniform = object : FloatUniform() {
        override val name = "u_radius"

        override val value get() = this@SsaoRenderPassPrototype.radius
    }

    override val shaders = arrayOf(
        Shader.createVertex(GlslVersion.V400, Renderers.vertexShaderCode),
        Shader.createFragment(
            GlslVersion.V400,
            ShaderCode.define("KERNEL_SAMPLES", this.kernel.size.toString()),
            ShaderCode.loadSource("/shaders/deferred/ssao/ssao.fragment.glsl")
        ),
    )

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
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

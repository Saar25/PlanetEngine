package org.saar.core.renderer.deferred.passes

import org.joml.Math
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.painting.Random2fPainter
import org.saar.core.renderer.*
import org.saar.core.renderer.state.RenderState
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.*
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.stencil.StencilTest
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
import org.saar.maths.utils.Vector2
import org.saar.maths.utils.Vector3
import kotlin.random.Random


class SSAOMapGenerator @JvmOverloads constructor(
    private val normalSpecularBuffer: ReadOnlyTexture2D,
    private val depthBuffer: ReadOnlyTexture2D,
    private val radius: Float = 5f,
    private val noiseTextureSize: Int = 64,
    private val kernelSamplesSize: Int = 32
) : RenderPass {

    private val noiseTexture = createNoiseTexture()

    private val kernel = createKernel(this.kernelSamplesSize)

    private val shadersLink = SsaoShadersLink(this.kernel.size)
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = StencilTestRenderState(StencilState.REPLACE)

    init {
        this.shadersLink.init()
        this.shadersLink.kernelUniform.value
            .forEachIndexed { index, uniform -> uniform.value = this.kernel[index] }
    }

    private fun createNoiseTexture(): MutableTexture2D {
        val texture = Random2fPainter().let { painter ->
            Renderers.renderToTexture(this.noiseTextureSize, this.noiseTextureSize, InternalFormat.R16F) {
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

    private fun createKernel(size: Int) = Array(size) { i ->
        val x = Random.nextFloat() * 2 - 1
        val y = Random.nextFloat() * 2 - 1
        val z = Random.nextFloat()

        val scale = Math.lerp(.1f, 1f, (i / 64f) * (i / 64f))

        Vector3.of(x, y, z).normalize(Random.nextFloat() * scale)
    }

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()
        this.shadersLink.normalSpecularTexture.value = this.normalSpecularBuffer
        this.shadersLink.depthTextureUniform.value = this.depthBuffer
        this.shadersLink.noiseTextureUniform.value = this.noiseTexture
        this.shadersLink.noiseScaleUniform.value = Vector2.of(
            MainScreen.width.toFloat(),
            MainScreen.height.toFloat()
        ).div(this.noiseTextureSize.toFloat())

        this.shadersLink.projectionMatrixInvUniform.value =
            context.camera.projection.matrix.invertPerspective(Matrix4.temp)

        this.shadersLink.projectionMatrixUniform.value.set(context.camera.projection.matrix)
        this.shadersLink.radiusUniform.value = this.radius

        this.uniformsLoader.load()
        QuadMesh.draw()
    }

    override fun delete() {
        this.shadersLink.shadersProgram.delete()
        this.noiseTexture.delete()
    }

    private class SsaoShadersLink(kernelSize: Int) : ShadersLink {

        @UniformProperty
        val normalSpecularTexture = TextureUniformValue("u_normalSpecularTexture", 0)

        @UniformProperty
        val depthTextureUniform = TextureUniformValue("u_depthTexture", 1)

        @UniformProperty
        val noiseTextureUniform = TextureUniformValue("u_noiseTexture", 2)

        @UniformProperty
        val kernelUniform = UniformArray("u_kernel", kernelSize, ::Vec3UniformValue)

        @UniformProperty
        val noiseScaleUniform = Vec2UniformValue("u_noiseScale")

        @UniformProperty
        val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

        @UniformProperty
        val projectionMatrixUniform = Mat4UniformValue("u_projectionMatrix")

        @UniformProperty
        val radiusUniform = FloatUniformValue("u_radius")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.define("KERNEL_SAMPLES", kernelSize.toString()),
                ShaderCode.loadSource("/shaders/deferred/ssao/ssao.fragment.glsl")
            ),
        )
    }
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

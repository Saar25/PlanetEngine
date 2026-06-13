package org.saar.core.postprocessing

import org.joml.Vector2i
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.*
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.utils.Maths.sqrt
import kotlin.math.PI
import kotlin.math.exp

private fun calculateGaussianKernel(samples: Int, sigma: Float): FloatArray {
    val mean = samples / 2f

    val twoSigmaSquare = 2.0f * sigma * sigma
    val normalizationFactor = 1.0f / (sqrt(2.0f * PI.toFloat()) * sigma)

    val kernel = FloatArray(samples) {
        val x = it.toFloat() - mean
        val exponent = -(x * x) / twoSigmaSquare

        normalizationFactor * exp(exponent)
    }

    val sum = kernel.sum()
    for (x in 0 until samples) {
        kernel[x] /= sum
    }
    return kernel
}

class GaussianBlurRenderPass(samples: Int = 11, sigma: Float = samples / 3f) {

    private val samples = calculateGaussianKernel(samples, sigma)
    private val prototype = GaussianBlurPostProcessorPrototype(this.samples)
    private val wrapper = RendererPrototypeHelper(this.prototype)

    inner class Vertical(private val albedoBuffer: ReadOnlyTexture2D) : RenderPass {
        override fun render(context: RenderContext) = this@GaussianBlurRenderPass.wrapper.render(context) {
            this@GaussianBlurRenderPass.prototype.textureUniform.value = this.albedoBuffer

            this@GaussianBlurRenderPass.prototype.verticalBlurUniform.value = true
        }

        override fun delete() = this@GaussianBlurRenderPass.wrapper.delete()
    }

    inner class Horizontal(private val albedoBuffer: ReadOnlyTexture2D) : RenderPass {
        override fun render(context: RenderContext) = this@GaussianBlurRenderPass.wrapper.render(context) {
            this@GaussianBlurRenderPass.prototype.textureUniform.value = this.albedoBuffer

            this@GaussianBlurRenderPass.prototype.verticalBlurUniform.value = false
        }

        override fun delete() = this@GaussianBlurRenderPass.wrapper.delete()
    }
}

private class GaussianBlurPostProcessorPrototype(private val samples: FloatArray) : RendererPrototype<Unit> {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    val resolutionUniform = object : Vec2iUniform() {
        override val name = "u_resolution"

        override val value = Vector2i()
            get() = field.set(MainScreen.width, MainScreen.height)
    }

    @UniformProperty
    val blurLevelsUniform = UniformArray("u_blurLevels", this.samples.size) { name, index ->
        FloatUniformValue(name, this.samples[index])
    }

    @UniformProperty
    val verticalBlurUniform = BooleanUniformValue("u_verticalBlur")

    override val shadersProgram: ShadersProgram = ShadersProgram.create(
        Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
        Shader.createFragment(
            GlslVersion.V400,
            ShaderCode.define("LEVELS", this.samples.size.toString()),
            ShaderCode.loadSource("/shaders/postprocessing/gaussian-blur.pass.glsl")
        ),
    )

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
}
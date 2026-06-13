package org.saar.core.postprocessing

import org.joml.Vector2i
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.*
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.pow

private fun calculateGaussianKernel(samples: Int, sigma: Int): FloatArray {
    val mean = samples / 2

    val kernel = FloatArray(samples) {
        val pow = ((it - mean) / sigma).toFloat().pow(2.0f)
        (exp(-0.5f * pow) / (2 * PI * sigma * sigma)).toFloat()
    }

    val sum = kernel.sum()
    for (x in 0 until samples) {
        kernel[x] /= sum
    }
    return kernel
}

class GaussianBlurPostProcessor(
    private val albedoBuffer: ReadOnlyTexture2D,
    samples: Int,
    sigma: Int
) : RenderPass {

    private val samples = calculateGaussianKernel(samples, sigma)
    private val prototype = GaussianBlurPostProcessorPrototype(this.samples)
    private val wrapper = RendererPrototypeHelper(this.prototype)

    override fun render(context: RenderContext) {
        this.wrapper.render(context) {
            this.prototype.textureUniform.value = this.albedoBuffer

            this.prototype.verticalBlurUniform.value = true
        }
        this.wrapper.render(context) {
            this.prototype.textureUniform.value = this.albedoBuffer

            this.prototype.verticalBlurUniform.value = false
        }
    }

    override fun delete() = this.wrapper.delete()
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

    override val shaders = arrayOf(
        Shader.createVertex(GlslVersion.V400, Renderers.vertexShaderCode),
        Shader.createFragment(
            GlslVersion.V400,
            ShaderCode.define("LEVELS", this.samples.size.toString()),
            ShaderCode.loadSource("/shaders/postprocessing/gaussian-blur.pass.glsl")
        ),
    )

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
}
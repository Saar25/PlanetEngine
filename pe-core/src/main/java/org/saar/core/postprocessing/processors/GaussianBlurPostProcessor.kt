package org.saar.core.postprocessing.processors

import org.joml.Vector2i
import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.postprocessing.PostProcessor
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.*
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

class GaussianBlurPostProcessor(samples: Int, sigma: Int) : PostProcessor {

    private val samples = calculateGaussianKernel(samples, sigma)
    private val prototype = GaussianBlurPostProcessorPrototype(this.samples)
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render(context: RenderContext, buffers: PostProcessingBuffers) {
        this.wrapper.render {
            this.prototype.textureUniform.value = buffers.albedo

            this.prototype.verticalBlurUniform.value = true
        }
        this.wrapper.render {
            this.prototype.textureUniform.value = buffers.albedo

            this.prototype.verticalBlurUniform.value = false
        }
    }

    override fun delete() {
        this.wrapper.delete()
    }
}

private class GaussianBlurPostProcessorPrototype(private val samples: FloatArray) : RenderPassPrototype {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    val resolutionUniform = object : Vec2iUniform() {
        override val name = "u_resolution"

        override val value = Vector2i()
            get() = field.set(MainScreen.width, MainScreen.height)
    }

    @UniformProperty
    val blurLevelsUniform: UniformArray<FloatUniform> = UniformArray("u_blurLevels", this.samples.size) { name, index ->
        FloatUniformValue(name, this.samples[index])
    }

    @UniformProperty
    val verticalBlurUniform = BooleanUniformValue("u_verticalBlur")

    override val fragmentShader: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("LEVELS", this.samples.size.toString()),
        ShaderCode.loadSource("/shaders/postprocessing/gaussian-blur.pass.glsl"))
}
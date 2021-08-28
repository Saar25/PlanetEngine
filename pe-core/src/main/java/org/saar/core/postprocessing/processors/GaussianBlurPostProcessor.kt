package org.saar.core.postprocessing.processors

import org.joml.Vector2i
import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.postprocessing.PostProcessorPrototype
import org.saar.core.postprocessing.PostProcessorPrototypeWrapper
import org.saar.core.renderer.renderpass.RenderPassContext
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

class GaussianBlurPostProcessor(samples: Int, sigma: Int) : PostProcessorPrototypeWrapper(
    GaussianBlurPostProcessorPrototype(calculateGaussianKernel(samples, sigma))) {

    override fun render(context: RenderPassContext, buffers: PostProcessingBuffers) {
        super.render(context, buffers)
        super.render(context, buffers)
    }
}

private class GaussianBlurPostProcessorPrototype(private val samples: FloatArray) : PostProcessorPrototype {

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    private val resolutionUniform = object : Vec2iUniform() {
        override fun getName() = "u_resolution"

        override fun getUniformValue() = Vector2i(MainScreen.width, MainScreen.height)
    }

    @UniformProperty
    private val blurLevelsUniform = UniformArray("u_blurLevels", this.samples.size) { name, index ->
        object : FloatUniform() {
            override fun getName() = name

            override fun getUniformValue() =
                this@GaussianBlurPostProcessorPrototype.samples[index]
        }
    }

    @UniformProperty
    private val verticalBlurUniform = IntUniformValue("u_verticalBlur")

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("LEVELS", this.samples.size.toString()),
        ShaderCode.loadSource("/shaders/postprocessing/gaussian-blur.pass.glsl"))

    override fun onRender(context: RenderPassContext, buffers: PostProcessingBuffers) {
        this.textureUniform.value = buffers.albedo

        val vertical = 1 - this.verticalBlurUniform.value
        this.verticalBlurUniform.value = vertical
    }
}
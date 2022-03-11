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
import org.saar.lwjgl.opengl.shaders.uniforms.FloatUniform
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.Vec2iUniform
import org.saar.lwjgl.opengl.stencil.StencilTest

class GammaCorrectionPostProcessor(gamma: Float = 2.2f) : PostProcessor {

    private val prototype = GammaCorrectionPostProcessorPrototype(gamma)
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render(context: RenderContext, buffers: PostProcessingBuffers) = this.wrapper.render {
        StencilTest.disable()

        this.prototype.textureUniform.value = buffers.albedo
    }

    override fun delete() {
        this.wrapper.delete()
    }
}

private class GammaCorrectionPostProcessorPrototype(private val gamma: Float) : RenderPassPrototype {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    val resolutionUniform = object : Vec2iUniform() {
        override val name = "u_resolution"

        override val value = Vector2i()
            get() = field.set(MainScreen.width, MainScreen.height)
    }

    @UniformProperty
    val gammaUniform = object : FloatUniform() {
        override val name = "u_gamma"

        override val value = gamma
    }

    override val fragmentShader: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/postprocessing/gamma-correction.pass.glsl"))
}
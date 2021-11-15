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
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.Vec2iUniform
import org.saar.lwjgl.opengl.stencil.StencilTest

class FxaaPostProcessor : PostProcessor {

    private val prototype = FxaaPostProcessorPrototype()
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render(context: RenderContext, buffers: PostProcessingBuffers) = this.wrapper.render {
        StencilTest.disable()

        this.prototype.textureUniform.value = buffers.albedo
    }

    override fun delete() {
        this.wrapper.delete()
    }
}

private class FxaaPostProcessorPrototype : RenderPassPrototype {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    val resolutionUniform = object : Vec2iUniform() {
        override val name = "u_resolution"

        override val value = Vector2i()
            get() = field.set(MainScreen.width, MainScreen.height)
    }

    override val fragmentShader: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("FXAA_REDUCE_MIN", (1.0 / 128.0).toString()),
        ShaderCode.define("FXAA_REDUCE_MUL", (1.0 / 8.0).toString()),
        ShaderCode.define("FXAA_SPAN_MAX", 8.0.toString()),
        ShaderCode.loadSource("/shaders/postprocessing/fxaa.pass.glsl"))
}
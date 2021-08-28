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
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.Vec2iUniform

class FxaaPostProcessor : PostProcessorPrototypeWrapper(FxaaPostProcessorPrototype())

private class FxaaPostProcessorPrototype : PostProcessorPrototype {

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    private val resolutionUniform = object : Vec2iUniform() {
        override fun getName() = "u_resolution"

        override fun getUniformValue() = Vector2i(MainScreen.width, MainScreen.height)
    }

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("FXAA_REDUCE_MIN", (1.0 / 128.0).toString()),
        ShaderCode.define("FXAA_REDUCE_MUL", (1.0 / 8.0).toString()),
        ShaderCode.define("FXAA_SPAN_MAX", 8.0.toString()),
        ShaderCode.loadSource("/shaders/postprocessing/fxaa.pass.glsl"))

    override fun onRender(context: RenderPassContext, buffers: PostProcessingBuffers) {
        this.textureUniform.value = buffers.albedo
    }

    override fun onDelete() {
    }
}
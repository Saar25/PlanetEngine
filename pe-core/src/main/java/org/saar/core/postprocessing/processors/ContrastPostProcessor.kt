package org.saar.core.postprocessing.processors

import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.postprocessing.PostProcessor
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.FloatUniform
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue

class ContrastPostProcessor(contrast: Float) : PostProcessor {

    private val prototype = ContrastPostProcessorPrototype(contrast)
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render(context: RenderPassContext, buffers: PostProcessingBuffers) = this.wrapper.render {
        this.prototype.textureUniform.value = buffers.albedo
    }

    override fun delete() {
        this.wrapper.delete()
    }
}

private class ContrastPostProcessorPrototype(contrast: Float) : RenderPassPrototype {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    val contrastUniform = object : FloatUniform() {
        override val name = "u_contrast"

        override val value = contrast
    }

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/postprocessing/contrast.pass.glsl"))
}
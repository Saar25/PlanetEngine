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
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniform
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture

class MultiplyPostProcessor(multiply: ReadOnlyTexture, components: Int = 4) : PostProcessor {

    private val prototype = MultiplyPostProcessorPrototype(multiply, components)
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render(context: RenderPassContext, buffers: PostProcessingBuffers) = this.wrapper.render {
        this.prototype.textureUniform.value = buffers.albedo
    }

    override fun delete() {
        this.wrapper.delete()
    }
}

private class MultiplyPostProcessorPrototype(multiply: ReadOnlyTexture,
                                             private val components: Int) : RenderPassPrototype {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    val multiplyUniform = object : TextureUniform() {
        override val unit = 1

        override val name = "u_multiply"

        override val value = multiply
    }

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("COMPONENTS", components.toString()),
        ShaderCode.loadSource("/shaders/postprocessing/multiply.pass.glsl"))
}
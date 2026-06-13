package org.saar.core.postprocessing

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderNode
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniform
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class MultiplyPostProcessor(
    private val albedoBuffer: ReadOnlyTexture2D,
    multiply: ReadOnlyTexture,
    components: Int = 4
) : RenderNode {

    private val prototype = MultiplyPostProcessorPrototype(multiply, components)
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render(context: RenderContext) = this.wrapper.render {
        this.prototype.textureUniform.value = this.albedoBuffer
    }

    override fun delete() = this.wrapper.delete()
}

private class MultiplyPostProcessorPrototype(multiply: ReadOnlyTexture, components: Int) : RenderPassPrototype {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    val multiplyUniform = object : TextureUniform() {
        override val unit = 1

        override val name = "u_multiply"

        override val value = multiply
    }

    override val fragmentShader: Shader = Shader.createFragment(
        GlslVersion.V400,
        ShaderCode.define("COMPONENTS", components.toString()),
        ShaderCode.loadSource("/shaders/postprocessing/multiply.pass.glsl")
    )
}
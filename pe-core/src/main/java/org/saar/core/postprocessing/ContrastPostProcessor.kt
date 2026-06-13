package org.saar.core.postprocessing

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderNode
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniform
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class ContrastPostProcessor(
    private val albedoBuffer: ReadOnlyTexture2D,
    contrast: Float,
) : RenderNode {

    private val prototype = ContrastPostProcessorPrototype(contrast)
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render(context: RenderContext) = this.wrapper.render {
        this.prototype.textureUniform.value = this.albedoBuffer
    }

    override fun delete() = this.wrapper.delete()
}

private class ContrastPostProcessorPrototype(contrast: Float) : RenderPassPrototype {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    val contrastUniform = object : FloatUniform() {
        override val name = "u_contrast"

        override val value = contrast
    }

    override val fragmentShader: Shader = Shader.createFragment(
        GlslVersion.V400,
        ShaderCode.loadSource("/shaders/postprocessing/contrast.pass.glsl")
    )
}
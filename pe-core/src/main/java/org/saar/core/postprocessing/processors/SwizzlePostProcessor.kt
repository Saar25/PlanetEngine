package org.saar.core.postprocessing.processors

import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.postprocessing.PostProcessor
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.stencil.StencilTest

class SwizzlePostProcessor(r: Swizzle, g: Swizzle, b: Swizzle, a: Swizzle) : PostProcessor {

    private val prototype = SwizzlePostProcessorPrototype(r, g, b, a)
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun prepare(context: RenderContext, buffers: PostProcessingBuffers) {
        StencilTest.disable()
    }

    override fun render(context: RenderContext, buffers: PostProcessingBuffers) = this.wrapper.render {
        this.prototype.textureUniform.value = buffers.albedo
    }

    override fun delete() {
        this.wrapper.delete()
    }
}

private class SwizzlePostProcessorPrototype(r: Swizzle, g: Swizzle, b: Swizzle, a: Swizzle) : RenderPassPrototype {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    override val fragmentShader: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("R", r.name.lowercase()),
        ShaderCode.define("G", g.name.lowercase()),
        ShaderCode.define("B", b.name.lowercase()),
        ShaderCode.define("A", a.name.lowercase()),
        ShaderCode.loadSource("/shaders/postprocessing/swizzle.pass.glsl"))
}
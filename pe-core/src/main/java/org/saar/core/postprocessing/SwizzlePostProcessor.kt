package org.saar.core.postprocessing

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class SwizzlePostProcessor(
    private val albedoBuffer: ReadOnlyTexture2D,
    r: Swizzle,
    g: Swizzle,
    b: Swizzle,
    a: Swizzle
) : RenderNode {

    private val prototype = SwizzlePostProcessorPrototype(r, g, b, a)
    private val wrapper = RendererPrototypeHelper(this.prototype)

    override val renderState = CompositeRenderState(
        StencilTestRenderState(StencilState.DISABLED)
    )

    override fun render(context: RenderContext) = this.wrapper.render(context) {
        this.prototype.textureUniform.value = this.albedoBuffer
    }

    override fun delete() = this.wrapper.delete()
}

private class SwizzlePostProcessorPrototype(r: Swizzle, g: Swizzle, b: Swizzle, a: Swizzle) : RendererPrototype<Unit> {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    override val shaders = arrayOf(
        Shader.createVertex(GlslVersion.V400, Renderers.vertexShaderCode),
        Shader.createFragment(
            GlslVersion.V400,
            ShaderCode.define("R", r.name.lowercase()),
            ShaderCode.define("G", g.name.lowercase()),
            ShaderCode.define("B", b.name.lowercase()),
            ShaderCode.define("A", a.name.lowercase()),
            ShaderCode.loadSource("/shaders/postprocessing/swizzle.pass.glsl")
        ),
    )

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
}
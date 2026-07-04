package org.saar.core.common.renderpass

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.DepthStencilRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.rhi.depthstencil.DepthStencilState

class SwizzlePostProcessor(
    private val albedoBuffer: ReadOnlyTexture2D,
    r: Swizzle, g: Swizzle, b: Swizzle, a: Swizzle
) : RenderPass {

    private val shadersLink = SwizzleShadersLink(r, g, b, a)
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = DepthStencilRenderState(DepthStencilState())

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()
        this.shadersLink.textureUniform.value = this.albedoBuffer

        this.uniformsLoader.load()
        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private class SwizzleShadersLink(r: Swizzle, g: Swizzle, b: Swizzle, a: Swizzle) : ShadersLink {

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.define("R", r.name.lowercase()),
                ShaderCode.define("G", g.name.lowercase()),
                ShaderCode.define("B", b.name.lowercase()),
                ShaderCode.define("A", a.name.lowercase()),
                ShaderCode.loadSource("/shaders/postprocessing/swizzle.pass.glsl")
            ),
        )
    }
}

enum class Swizzle {
    R, G, B, A
}
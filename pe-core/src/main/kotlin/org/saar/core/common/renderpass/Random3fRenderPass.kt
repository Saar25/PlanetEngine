package org.saar.core.common.renderpass

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.Renderers
import org.saar.core.renderer.ShadersLink
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram

class Random3fRenderPass : RenderPass {

    private val shadersLink = Random3fPainterPrototype

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()

        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object Random3fPainterPrototype : ShadersLink {

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
            Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/painting/random3f.fragment.glsl")),
        )
    }
}
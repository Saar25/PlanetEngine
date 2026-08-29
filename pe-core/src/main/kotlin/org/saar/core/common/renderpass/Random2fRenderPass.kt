package org.saar.core.common.renderpass

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.Renderers
import org.saar.core.renderer.ShadersLink
import org.saar.rhi.opengl.shader.toOpengl
import org.saar.rhi.shader.*

class Random2fRenderPass : RenderPass {

    private val shadersLink = Random2fPainterPrototype

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()

        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object Random2fPainterPrototype : ShadersLink {

        override val shadersProgram = ShaderProgram(
            ShaderStage(
                type = ShaderStageType.VERTEX,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + Renderers.quadVertexSource)
            ),
            ShaderStage(
                type = ShaderStageType.FRAGMENT,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + ShaderModuleLoader.loadSource("/shaders/painting/random2f.fragment.glsl"))
            ),
        ).toOpengl()
    }
}
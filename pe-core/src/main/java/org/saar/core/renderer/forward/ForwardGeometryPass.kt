package org.saar.core.renderer.forward

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.CullFaceRenderState
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.lwjgl.opengl.cullface.CullFaceState
import org.saar.lwjgl.opengl.stencil.StencilState

class ForwardGeometryPass(private vararg val children: ForwardRenderNode) : RenderPass {

    override val renderState = CompositeRenderState(
        StencilTestRenderState(StencilState.ALWAYS_WRITE),
        CullFaceRenderState(CullFaceState.BACK_CCW),
    )

    override fun render(context: RenderContext) = this.children.forEach { it.renderForward(context) }

    override fun delete() = this.children.forEach { it.delete() }
}
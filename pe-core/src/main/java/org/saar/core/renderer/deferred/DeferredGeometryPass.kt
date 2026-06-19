package org.saar.core.renderer.deferred

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.CullFaceRenderState
import org.saar.core.renderer.state.DepthTestRenderState
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.lwjgl.opengl.cullface.CullFaceState
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.stencil.StencilState

class DeferredGeometryPass(private vararg val children: DeferredRenderNode) : RenderPass {

    override val renderState = CompositeRenderState(
        DepthTestRenderState(DepthState.WRITE),
        StencilTestRenderState(StencilState.ALWAYS_WRITE),
        CullFaceRenderState(CullFaceState.BACK_CCW),
    )

    override fun render(context: RenderContext) = this.children.forEach { it.renderDeferred(context) }

    override fun delete() = this.children.forEach { it.delete() }
}
package org.saar.core.renderer.forward

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.CullFaceRenderState
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.lwjgl.opengl.cullface.CullFaceState
import org.saar.lwjgl.opengl.stencil.StencilState

class ForwardNodeRenderPass(
    private val camera: ICamera,
    private val renderNode: ForwardRenderNode
) : RenderPass {

    override val renderState = CompositeRenderState(
        StencilTestRenderState(StencilState.ALWAYS_WRITE),
        CullFaceRenderState(CullFaceState.BACK_CCW),
    )

    override fun render(context: RenderContext) {
        val forwardContext = ForwardRenderContext(context, this.camera)
        this.renderNode.renderForward(forwardContext)
    }

    override fun delete() = this.renderNode.delete()
}

fun ForwardRenderNode.asForwardRenderPass(camera: ICamera) = ForwardNodeRenderPass(camera, this)

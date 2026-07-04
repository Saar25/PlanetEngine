package org.saar.core.renderer.forward

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.RasterizationRenderState
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.rhi.resterization.CullMode
import org.saar.rhi.resterization.FrontFace
import org.saar.rhi.resterization.RasterizationState

class ForwardNodeRenderPass(
    private val camera: ICamera,
    private val renderNode: ForwardRenderNode
) : RenderPass {

    override val renderState = CompositeRenderState(
        StencilTestRenderState(StencilState.ALWAYS_WRITE),
        RasterizationRenderState(RasterizationState(cullMode = CullMode.BACK, frontFace = FrontFace.COUNTER_CLOCKWISE))
    )

    override fun render(context: RenderContext) {
        val forwardContext = ForwardRenderContext(context, this.camera)
        this.renderNode.renderForward(forwardContext)
    }

    override fun delete() = this.renderNode.delete()
}

fun ForwardRenderNode.asForwardRenderPass(camera: ICamera) = ForwardNodeRenderPass(camera, this)

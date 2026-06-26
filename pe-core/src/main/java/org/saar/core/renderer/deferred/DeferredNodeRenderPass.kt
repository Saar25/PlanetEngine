package org.saar.core.renderer.deferred

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.CullFaceRenderState
import org.saar.core.renderer.state.DepthTestRenderState
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.lwjgl.opengl.cullface.CullFaceState
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.stencil.StencilState

fun RenderGraph.Builder.deferredNodePass(
    camera: ICamera, renderNode: DeferredRenderNode
): DeferredNodeRenderPass {
    val renderPass = DeferredNodeRenderPass(camera, renderNode)
    addPass(renderPass)
    return renderPass
}

class DeferredNodeRenderPass(
    private val camera: ICamera,
    private val renderNode: DeferredRenderNode
) : RenderPass {

    constructor(camera: ICamera, vararg children: DeferredRenderNode) : this(camera, DeferredRenderNodeGroup(*children))

    override val renderState = CompositeRenderState(
        DepthTestRenderState(DepthState.WRITE),
        StencilTestRenderState(StencilState.ALWAYS_WRITE),
        CullFaceRenderState(CullFaceState.BACK_CCW),
    )

    override fun render(context: RenderContext) {
        val deferredContext = DeferredRenderContext(context, this.camera)
        this.renderNode.renderDeferred(deferredContext)
    }

    override fun delete() = this.renderNode.delete()
}

fun DeferredRenderNode.asDeferredRenderPass(camera: ICamera) = DeferredNodeRenderPass(camera, this)
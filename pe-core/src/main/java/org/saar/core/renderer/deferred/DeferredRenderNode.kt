package org.saar.core.renderer.deferred

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass

interface DeferredRenderNode : Node {

    fun renderDeferred(context: RenderContext)

}

fun DeferredRenderNode.asDeferredRenderNode() = object : RenderPass {
    override fun render(context: RenderContext) = this@asDeferredRenderNode.renderDeferred(context)

    override fun delete() = this@asDeferredRenderNode.delete()
}
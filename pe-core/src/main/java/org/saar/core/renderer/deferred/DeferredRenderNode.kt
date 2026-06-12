package org.saar.core.renderer.deferred

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderNode

interface DeferredRenderNode : Node {

    fun renderDeferred(context: RenderContext)

}

fun DeferredRenderNode.asDeferredRenderNode() = object : RenderNode {
    override fun render(context: RenderContext) = this@asDeferredRenderNode.renderDeferred(context)

    override fun delete() = this@asDeferredRenderNode.delete()
}
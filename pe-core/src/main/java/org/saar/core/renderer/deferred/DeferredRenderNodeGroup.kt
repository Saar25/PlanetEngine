package org.saar.core.renderer.deferred

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext

class DeferredRenderNodeGroup(vararg children: DeferredRenderNode) : ParentNode, DeferredRenderNode {

    override val children: MutableList<DeferredRenderNode> = children.toMutableList()

    override fun renderDeferred(context: RenderContext) = this.children.forEach { it.renderDeferred(context) }
}
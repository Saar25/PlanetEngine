package org.saar.core.renderer.deferred

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderNode

class DeferredRenderNodeGroup(vararg children: DeferredRenderNode) : ParentNode, DeferredRenderNode, RenderNode {

    override val children: MutableList<DeferredRenderNode> = children.toMutableList()

    override fun renderDeferred(context: RenderContext) = render(context)

    override fun render(context: RenderContext) = this.children.forEach { it.renderDeferred(context) }

    override fun delete() = this.children.forEach { it.delete() }
}
package org.saar.core.renderer.forward

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext

class ForwardRenderNodeGroup(vararg children: ForwardRenderNode) : ParentNode, ForwardRenderNode {

    override val children: List<ForwardRenderNode> = children.toMutableList()

    override fun renderForward(context: RenderContext) = this.children.forEach { it.renderForward(context) }
}
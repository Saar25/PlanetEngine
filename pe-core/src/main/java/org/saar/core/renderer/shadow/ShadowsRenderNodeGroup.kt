package org.saar.core.renderer.shadow

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext

class ShadowsRenderNodeGroup(vararg children: ShadowsRenderNode) : ParentNode, ShadowsRenderNode {

    override val children: MutableList<ShadowsRenderNode> = children.toMutableList()

    override fun renderShadows(context: RenderContext) = this.children.forEach { it.renderShadows(context) }
}
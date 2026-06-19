package org.saar.core.renderer.shadow

import org.saar.core.node.ParentNode

class ShadowsRenderNodeGroup(vararg children: ShadowsRenderNode) : ParentNode, ShadowsRenderNode {

    override val children: MutableList<ShadowsRenderNode> = children.toMutableList()

    override fun renderShadows(context: ShadowsRenderContext) = this.children.forEach { it.renderShadows(context) }

    override fun delete() = this.children.forEach { it.delete() }
}
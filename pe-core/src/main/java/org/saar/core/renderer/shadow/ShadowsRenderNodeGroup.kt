package org.saar.core.renderer.shadow

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass

class ShadowsRenderNodeGroup(vararg children: ShadowsRenderNode) : ParentNode, ShadowsRenderNode, RenderPass {

    override val children: MutableList<ShadowsRenderNode> = children.toMutableList()

    override fun renderShadows(context: RenderContext) = render(context)

    override fun render(context: RenderContext) = this.children.forEach { it.renderShadows(context) }

    override fun delete() = this.children.forEach { it.delete() }
}
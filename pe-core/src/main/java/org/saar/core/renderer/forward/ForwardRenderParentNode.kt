package org.saar.core.renderer.forward

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext

interface ForwardRenderParentNode : ParentNode, ForwardRenderNode {
    override val children: List<ForwardRenderNode>

    override fun renderForward(context: RenderContext) {
        this.children.forEach { it.renderForward(context) }
    }
}
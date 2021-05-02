package org.saar.core.renderer.deferred

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext

interface DeferredRenderParentNode : ParentNode, DeferredRenderNode {
    override val children: List<DeferredRenderNode>

    override fun renderDeferred(context: RenderContext) {
        this.children.forEach { it.renderDeferred(context) }
    }
}
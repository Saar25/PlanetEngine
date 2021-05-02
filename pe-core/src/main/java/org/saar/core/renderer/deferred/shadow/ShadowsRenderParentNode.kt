package org.saar.core.renderer.deferred.shadow

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext

interface ShadowsRenderParentNode : ParentNode, ShadowsRenderNode {
    override val children: List<ShadowsRenderNode>

    override fun renderShadows(context: RenderContext) {
        this.children.forEach { it.renderShadows(context) }
    }
}
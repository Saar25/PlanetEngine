package org.saar.core.renderer.pbr

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext

interface PBRenderParentNode : ParentNode, PBRenderNode {
    override val children: List<PBRenderNode>

    override fun renderPBR(context: RenderContext) {
        this.children.forEach { it.renderPBR(context) }
    }
}
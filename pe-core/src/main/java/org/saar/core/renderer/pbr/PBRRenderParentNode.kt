package org.saar.core.renderer.pbr

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext

interface PBRRenderParentNode : ParentNode, PBRRenderNode {
    override val children: List<PBRRenderNode>

    override fun renderPBR(context: RenderContext) {
        this.children.forEach { it.renderPBR(context) }
    }
}
package org.saar.core.common.r2d

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.forward.ForwardRenderParentNode

class NodeBatch2D(vararg nodes: Node2D) : ParentNode, ForwardRenderParentNode {

    override val children: MutableList<Node2D> = nodes.toMutableList()

    fun add(node: Node2D) {
        this.children.add(node)
    }

    override fun renderForward(context: RenderContext) {
        val models = this.children.map { it.model }.toTypedArray()
        Renderer2D.render(context, *models)
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}
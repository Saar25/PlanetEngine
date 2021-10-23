package org.saar.core.common.r2d

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.p2d.RenderNode2D

class NodeBatch2D(vararg nodes: Node2D) : ParentNode, ForwardRenderNode, RenderNode2D {

    override val children: MutableList<Node2D> = nodes.toMutableList()

    fun add(node: Node2D) {
        this.children.add(node)
    }

    override fun render2D(context: RenderContext) {
        val models = this.children.map { it.model }
        Renderer2D.render(context, models)
    }

    override fun renderForward(context: RenderContext) {
        val models = this.children.map { it.model }
        Renderer2D.render(context, models)
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}
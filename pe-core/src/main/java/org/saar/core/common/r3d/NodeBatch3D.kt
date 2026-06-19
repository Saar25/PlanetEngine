package org.saar.core.common.r3d

import org.saar.core.node.ParentNode
import org.saar.core.renderer.deferred.DeferredRenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderContext
import org.saar.core.renderer.shadow.ShadowsRenderNode

class NodeBatch3D(vararg nodes: Node3D) : ParentNode,
    ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override val children: MutableList<Node3D> = nodes.toMutableList()

    fun add(node: Node3D) {
        this.children.add(node)
    }

    override fun renderForward(context: ForwardRenderContext) {
        val models = this.children.map { it.model }
        Renderer3D.render(context, models)
    }

    override fun renderDeferred(context: DeferredRenderContext) {
        val models = this.children.map { it.model }
        DeferredRenderer3D.render(context, models)
    }

    override fun renderShadows(context: ShadowsRenderContext) {
        val models = this.children.map { it.model }
        DeferredRenderer3D.render(context, models)
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}
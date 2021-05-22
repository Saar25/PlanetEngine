package org.saar.core.common.flatreflected

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderParentNode
import org.saar.core.renderer.forward.ForwardRenderParentNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class FlatReflectedNodeBatch(vararg nodes: FlatReflectedNode) : ParentNode,
    ForwardRenderParentNode, DeferredRenderParentNode, ShadowsRenderNode {

    override val children: MutableList<FlatReflectedNode> = nodes.toMutableList()

    fun add(node: FlatReflectedNode) {
        this.children.add(node)
    }

    override fun renderForward(context: RenderContext) {
        val models = this.children.map { it.model }
        FlatReflectedRenderer.render(context, models)
    }

    override fun renderDeferred(context: RenderContext) {
        val models = this.children.map { it.model }
        FlatReflectedDeferredRenderer.render(context, models)
    }

    override fun renderShadows(context: RenderContext) {
        val models = this.children.map { it.model }
        FlatReflectedDeferredRenderer.render(context, models)
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}
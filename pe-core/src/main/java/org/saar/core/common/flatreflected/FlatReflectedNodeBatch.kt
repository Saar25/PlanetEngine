package org.saar.core.common.flatreflected

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderParentNode
import org.saar.core.renderer.deferred.shadow.ShadowsRenderNode
import org.saar.core.renderer.forward.ForwardRenderParentNode

class FlatReflectedNodeBatch(vararg nodes: FlatReflectedNode) : ParentNode,
    ForwardRenderParentNode, DeferredRenderParentNode, ShadowsRenderNode {

    override val children: MutableList<FlatReflectedNode> = nodes.toMutableList()

    private val forwardRenderer = lazy { FlatReflectedRenderer() }
    private val deferredRenderer = lazy { FlatReflectedDeferredRenderer() }

    fun add(node: FlatReflectedNode) {
        this.children.add(node)
    }

    override fun renderForward(context: RenderContext) {
        val models = this.children.map { it.model }.toTypedArray()
        this.forwardRenderer.value.render(context, *models)
    }

    override fun renderDeferred(context: RenderContext) {
        val models = this.children.map { it.model }.toTypedArray()
        this.deferredRenderer.value.render(context, *models)
    }

    override fun renderShadows(context: RenderContext) {
        val models = this.children.map { it.model }.toTypedArray()
        this.deferredRenderer.value.render(context, *models)
    }

    override fun delete() {
        this.children.forEach { it.delete() }

        if (this.forwardRenderer.isInitialized()) {
            this.forwardRenderer.value.delete()
        }
        if (this.deferredRenderer.isInitialized()) {
            this.deferredRenderer.value.delete()
        }
    }
}
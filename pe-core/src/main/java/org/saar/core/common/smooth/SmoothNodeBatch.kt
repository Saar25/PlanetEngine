package org.saar.core.common.smooth

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderParentNode
import org.saar.core.renderer.deferred.shadow.ShadowsRenderNode

class SmoothNodeBatch(vararg nodes: SmoothNode) : ParentNode, DeferredRenderParentNode, ShadowsRenderNode {

    override val children: MutableList<SmoothNode> = nodes.toMutableList()

    private val deferredRenderer = lazy { SmoothDeferredRenderer() }

    fun add(node: SmoothNode) {
        this.children.add(node)
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
        if (this.deferredRenderer.isInitialized()) {
            this.deferredRenderer.value.delete()
        }
    }
}
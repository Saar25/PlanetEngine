package org.saar.core.common.normalmap

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderParentNode
import org.saar.core.renderer.deferred.shadow.ShadowsRenderNode
import org.saar.core.renderer.forward.ForwardRenderParentNode

class NormalMappedNodeBatch(vararg nodes: NormalMappedNode) : ParentNode,
    ForwardRenderParentNode, DeferredRenderParentNode, ShadowsRenderNode {

    override val children: MutableList<NormalMappedNode> = nodes.toMutableList()

    private val forwardRenderer = lazy { NormalMappedRenderer() }
    private val deferredRenderer = lazy { NormalMappedDeferredRenderer() }

    fun add(node: NormalMappedNode) {
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
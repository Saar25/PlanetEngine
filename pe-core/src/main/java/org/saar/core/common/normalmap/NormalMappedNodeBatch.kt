package org.saar.core.common.normalmap

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class NormalMappedNodeBatch(vararg nodes: NormalMappedNode) : ParentNode,
    ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override val children: MutableList<NormalMappedNode> = nodes.toMutableList()

    fun add(node: NormalMappedNode) {
        this.children.add(node)
    }

    override fun renderForward(context: RenderContext) {
        val models = this.children.map { it.model }
        NormalMappedRenderer.render(context, models)
    }

    override fun renderDeferred(context: RenderContext) {
        val models = this.children.map { it.model }
        NormalMappedDeferredRenderer.render(context, models)
    }

    override fun renderShadows(context: RenderContext) {
        val models = this.children.map { it.model }
        NormalMappedDeferredRenderer.render(context, models)
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}
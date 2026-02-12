package org.saar.core.common.texture3d

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class Texture3DNodeBatch(vararg nodes: Texture3DNode) : ParentNode,
    ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override val children: MutableList<Texture3DNode> = nodes.toMutableList()

    fun add(node: Texture3DNode) {
        this.children.add(node)
    }

    override fun renderForward(context: RenderContext) {
        val models = this.children.map { it.model }
        Texture3DRenderer.render(context, models)
    }

    override fun renderDeferred(context: RenderContext) {
        val models = this.children.map { it.model }
        Texture3DDeferredRenderer.render(context, models)
    }

    override fun renderShadows(context: RenderContext) {
        val models = this.children.map { it.model }
        Texture3DDeferredRenderer.render(context, models)
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}
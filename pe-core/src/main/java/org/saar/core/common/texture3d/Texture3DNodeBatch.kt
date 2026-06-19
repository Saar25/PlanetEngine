package org.saar.core.common.texture3d

import org.saar.core.node.ParentNode
import org.saar.core.renderer.deferred.DeferredRenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderContext
import org.saar.core.renderer.shadow.ShadowsRenderNode

class Texture3DNodeBatch(vararg nodes: Texture3DNode) : ParentNode,
    ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override val children: MutableList<Texture3DNode> = nodes.toMutableList()

    fun add(node: Texture3DNode) {
        this.children.add(node)
    }

    override fun renderForward(context: ForwardRenderContext) {
        val models = this.children.map { it.model }
        Texture3DRenderer.render(context, models)
    }

    override fun renderDeferred(context: DeferredRenderContext) {
        val models = this.children.map { it.model }
        Texture3DDeferredRenderer.render(context, models)
    }

    override fun renderShadows(context: ShadowsRenderContext) {
        val models = this.children.map { it.model }
        val deferredRenderContext = DeferredRenderContext(context, context.camera)
        Texture3DDeferredRenderer.render(deferredRenderContext, models)
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}
package org.saar.core.common.obj

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderParentNode
import org.saar.core.renderer.forward.ForwardRenderParentNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class ObjNodeBatch(vararg nodes: ObjNode) : ParentNode,
    ForwardRenderParentNode, DeferredRenderParentNode, ShadowsRenderNode {

    override val children: MutableList<ObjNode> = nodes.toMutableList()

    fun add(node: ObjNode) {
        this.children.add(node)
    }

    override fun renderForward(context: RenderContext) {
        val models = this.children.map { it.model }.toTypedArray()
        ObjRenderer.render(context, *models)
    }

    override fun renderDeferred(context: RenderContext) {
        val models = this.children.map { it.model }.toTypedArray()
        ObjDeferredRenderer.render(context, *models)
    }

    override fun renderShadows(context: RenderContext) {
        val models = this.children.map { it.model }.toTypedArray()
        ObjDeferredRenderer.render(context, *models)
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}
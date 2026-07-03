package org.saar.core.common.obj

import org.saar.core.node.ParentNode
import org.saar.core.renderer.deferred.DeferredRenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderContext
import org.saar.core.renderer.shadow.ShadowsRenderNode

class ObjNodeBatch(override val children: Iterable<ObjNode>) : ParentNode,
    ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    constructor(vararg nodes: ObjNode) : this(nodes.asIterable())

    override fun renderForward(context: ForwardRenderContext) {
        val models = this.children.map { it.model }
        ObjRenderer.render(context, models)
    }

    override fun renderDeferred(context: DeferredRenderContext) {
        val models = this.children.map { it.model }
        ObjDeferredRenderer.render(context, models)
    }

    override fun renderShadows(context: ShadowsRenderContext) {
        val models = this.children.map { it.model }
        val deferredRenderContext = DeferredRenderContext(context, context.camera)
        ObjDeferredRenderer.render(deferredRenderContext, models)
    }

    override fun delete() = this.children.forEach { it.delete() }
}
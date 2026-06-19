package org.saar.core.common.obj

import org.saar.core.node.Node
import org.saar.core.renderer.deferred.DeferredRenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderContext
import org.saar.core.renderer.shadow.ShadowsRenderNode

class ObjNode(val model: ObjModel) : Node, ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override fun renderForward(context: ForwardRenderContext) {
        ObjRenderer.render(context, this.model)
    }

    override fun renderDeferred(context: DeferredRenderContext) {
        ObjDeferredRenderer.render(context, this.model)
    }

    override fun renderShadows(context: ShadowsRenderContext) {
        ObjDeferredRenderer.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
    }
}
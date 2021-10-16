package org.saar.core.common.obj

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class ObjNode(val model: ObjModel) : Node, ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override fun renderForward(context: RenderContext) {
        ObjRenderer.render(context, this.model)
    }

    override fun renderDeferred(context: RenderContext) {
        ObjDeferredRenderer.render(context, this.model)
    }

    override fun renderShadows(context: RenderContext) {
        ObjDeferredRenderer.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
    }
}
package org.saar.core.common.obj

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode

class ObjNode(val model: ObjModel) : Node, ForwardRenderNode, DeferredRenderNode {

    private val forwardRenderer = lazy { ObjRenderer() }
    private val deferredRenderer = lazy { ObjDeferredRenderer() }

    override fun renderForward(context: RenderContext) {
        this.forwardRenderer.value.render(context, this.model)
    }

    override fun renderDeferred(context: RenderContext) {
        this.deferredRenderer.value.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
        if (this.forwardRenderer.isInitialized()) {
            this.forwardRenderer.value.delete()
        }
        if (this.deferredRenderer.isInitialized()) {
            this.deferredRenderer.value.delete()
        }
    }
}
package org.saar.core.common.smooth

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.deferred.shadow.ShadowsRenderNode

class SmoothNode(val model: SmoothModel) : Node, DeferredRenderNode, ShadowsRenderNode {

    private val deferredRenderer = lazy { SmoothDeferredRenderer() }

    override fun renderDeferred(context: RenderContext) {
        this.deferredRenderer.value.render(context, this.model)
    }

    override fun renderShadows(context: RenderContext) {
        this.deferredRenderer.value.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
        if (this.deferredRenderer.isInitialized()) {
            this.deferredRenderer.value.delete()
        }
    }
}
package org.saar.core.common.smooth

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class SmoothNode(val model: SmoothModel) : Node, DeferredRenderNode, ShadowsRenderNode {

    override fun renderDeferred(context: RenderContext) {
        SmoothDeferredRenderer.render(context, this.model)
    }

    override fun renderShadows(context: RenderContext) {
        SmoothDeferredRenderer.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
    }
}
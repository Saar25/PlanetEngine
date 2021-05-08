package org.saar.core.common.smooth

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

open class SmoothNode(val model: SmoothModel) : Node, DeferredRenderNode, ShadowsRenderNode {

    final override fun renderDeferred(context: RenderContext) {
        SmoothDeferredRenderer.render(context, this.model)
    }

    final override fun renderShadows(context: RenderContext) {
        SmoothDeferredRenderer.render(context, this.model)
    }

    final override fun delete() {
        this.model.delete()
    }
}
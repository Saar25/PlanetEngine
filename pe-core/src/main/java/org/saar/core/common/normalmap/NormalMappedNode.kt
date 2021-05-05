package org.saar.core.common.normalmap

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode

class NormalMappedNode(val model: NormalMappedModel) : Node,
    ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    private val forwardRenderer = lazy { NormalMappedRenderer() }
    private val deferredRenderer = lazy { NormalMappedDeferredRenderer() }

    override fun renderForward(context: RenderContext) {
        this.forwardRenderer.value.render(context, this.model)
    }

    override fun renderDeferred(context: RenderContext) {
        this.deferredRenderer.value.render(context, this.model)
    }

    override fun renderShadows(context: RenderContext) {
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
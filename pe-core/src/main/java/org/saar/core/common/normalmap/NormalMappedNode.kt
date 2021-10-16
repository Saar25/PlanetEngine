package org.saar.core.common.normalmap

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class NormalMappedNode(val model: NormalMappedModel) : Node,
    ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override fun renderForward(context: RenderContext) {
        NormalMappedRenderer.render(context, this.model)
    }

    override fun renderDeferred(context: RenderContext) {
        NormalMappedDeferredRenderer.render(context, this.model)
    }

    override fun renderShadows(context: RenderContext) {
        NormalMappedDeferredRenderer.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
    }
}
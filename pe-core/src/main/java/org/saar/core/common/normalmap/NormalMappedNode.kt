package org.saar.core.common.normalmap

import org.saar.core.node.Node
import org.saar.core.renderer.deferred.DeferredRenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderContext
import org.saar.core.renderer.shadow.ShadowsRenderNode

class NormalMappedNode(val model: NormalMappedModel) : Node,
    ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override fun renderForward(context: ForwardRenderContext) {
        NormalMappedRenderer.render(context, this.model)
    }

    override fun renderDeferred(context: DeferredRenderContext) {
        NormalMappedDeferredRenderer.render(context, this.model)
    }

    override fun renderShadows(context: ShadowsRenderContext) {
        NormalMappedDeferredRenderer.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
    }
}
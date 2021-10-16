package org.saar.core.common.flatreflected

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class FlatReflectedNode(val model: FlatReflectedModel) : Node,
    ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override fun renderForward(context: RenderContext) {
        FlatReflectedRenderer.render(context, this.model)
    }

    override fun renderDeferred(context: RenderContext) {
        FlatReflectedDeferredRenderer.render(context, this.model)
    }

    override fun renderShadows(context: RenderContext) {
        FlatReflectedDeferredRenderer.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
    }
}
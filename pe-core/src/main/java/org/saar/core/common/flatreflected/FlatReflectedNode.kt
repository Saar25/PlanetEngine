package org.saar.core.common.flatreflected

import org.saar.core.node.Node
import org.saar.core.renderer.deferred.DeferredRenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderContext
import org.saar.core.renderer.shadow.ShadowsRenderNode

class FlatReflectedNode(val model: FlatReflectedModel) : Node,
    ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override fun renderForward(context: ForwardRenderContext) {
        FlatReflectedRenderer.render(context, this.model)
    }

    override fun renderDeferred(context: DeferredRenderContext) {
        FlatReflectedDeferredRenderer.render(context, this.model)
    }

    override fun renderShadows(context: ShadowsRenderContext) {
        FlatReflectedDeferredRenderer.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
    }
}
package org.saar.core.common.r3d

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class Node3D(val model: Model3D) : Node, ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override fun renderForward(context: RenderContext) {
        Renderer3D.render(context, this.model)
    }

    override fun renderDeferred(context: RenderContext) {
        DeferredRenderer3D.render(context, this.model)
    }

    override fun renderShadows(context: RenderContext) {
        DeferredRenderer3D.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
    }
}
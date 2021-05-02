package org.saar.core.common.r2d

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.forward.ForwardRenderNode

class Node2D(val model: Model2D) : Node, ForwardRenderNode {

    private val forwardRenderer = lazy { Renderer2D() }

    override fun renderForward(context: RenderContext) {
        this.forwardRenderer.value.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
        if (this.forwardRenderer.isInitialized()) {
            this.forwardRenderer.value.delete()
        }
    }
}
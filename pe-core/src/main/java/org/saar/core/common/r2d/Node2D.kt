package org.saar.core.common.r2d

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.forward.ForwardRenderNode

class Node2D(val model: Model2D) : Node, ForwardRenderNode {

    override fun renderForward(context: RenderContext) {
        Renderer2D.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
    }
}
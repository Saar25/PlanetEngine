package org.saar.core.common.r2d

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.p2d.RenderNode2D

class Node2D(val model: Model2D) : Node, RenderNode2D, ForwardRenderNode {

    override fun render2D(context: RenderContext) {
        Renderer2D.render(context, this.model)
    }

    override fun renderForward(context: RenderContext) {
        Renderer2D.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
    }
}
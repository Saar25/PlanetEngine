package org.saar.core.common.r2d

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass

class Node2D(val model: Model2D) : Node, RenderPass {

    override fun render(context: RenderContext) = Renderer2D.render(context, this.model)

    override fun delete() = this.model.delete()
}
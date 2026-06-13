package org.saar.core.renderer.forward

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass

interface ForwardRenderNode : Node {

    fun renderForward(context: RenderContext)

}

fun ForwardRenderNode.asForwardRenderNode() = object : RenderPass {
    override fun render(context: RenderContext) = this@asForwardRenderNode.renderForward(context)

    override fun delete() = this@asForwardRenderNode.delete()
}
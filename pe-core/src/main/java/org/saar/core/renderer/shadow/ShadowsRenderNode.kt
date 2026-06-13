package org.saar.core.renderer.shadow

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass

interface ShadowsRenderNode : Node {

    fun renderShadows(context: RenderContext)

}

fun ShadowsRenderNode.asShadowsRenderNode() = object : RenderPass {
    override fun render(context: RenderContext) = this@asShadowsRenderNode.renderShadows(context)

    override fun delete() = this@asShadowsRenderNode.delete()
}
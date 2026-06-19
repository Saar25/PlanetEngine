package org.saar.core.renderer.shadow

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass

class ShadowsNodeRenderPass(private val renderNode: ShadowsRenderNode) : RenderPass {

    override fun render(context: RenderContext) = this.renderNode.renderShadows(context)

    override fun delete() = this.renderNode.delete()
}

fun ShadowsRenderNode.asShadowsRenderPass() = ShadowsNodeRenderPass(this)
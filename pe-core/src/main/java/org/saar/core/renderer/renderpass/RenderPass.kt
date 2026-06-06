package org.saar.core.renderer.renderpass

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderNode

interface RenderPass<in T : RenderPassBuffers> {

    fun prepare(context: RenderContext, buffers: T) = Unit

    fun render(context: RenderContext, buffers: T)

    fun delete()

}

fun <T : RenderPassBuffers> RenderPass<T>.asRenderNode(buffers: T) = object : RenderNode {
    override fun render(context: RenderContext) = this@asRenderNode.render(context, buffers)

    override fun delete() = this@asRenderNode.delete()
}

fun RenderPass<RenderPassBuffers>.asRenderNode() = object : RenderNode {
    override fun render(context: RenderContext) = this@asRenderNode.render(context, object : RenderPassBuffers {})

    override fun delete() = this@asRenderNode.delete()
}
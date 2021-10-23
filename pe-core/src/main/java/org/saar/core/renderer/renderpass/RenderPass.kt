package org.saar.core.renderer.renderpass

interface RenderPass<in T : RenderPassBuffers> {

    fun prepare(context: RenderPassContext, buffers: T) = Unit

    fun render(context: RenderPassContext, buffers: T)

    fun delete()

}
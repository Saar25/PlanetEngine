package org.saar.core.renderer.renderpass

interface RenderPass<T : RenderPassRenderingBuffers> {

    fun render(context: RenderPassContext, buffers: T)

    fun delete()
}
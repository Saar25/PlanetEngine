package org.saar.core.renderer.forward

import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPass
import org.saar.core.renderer.renderpass.RenderPassContext

interface ForwardRenderPass : RenderPass, DeferredRenderPass {
    fun render(context: RenderPassContext, buffers: ForwardRenderingBuffers)

    override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        render(context, ForwardRenderingBuffers(buffers.albedo, buffers.depth))
    }
}
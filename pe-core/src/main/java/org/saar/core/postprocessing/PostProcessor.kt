package org.saar.core.postprocessing

import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.forward.ForwardRenderPass
import org.saar.core.renderer.forward.ForwardRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPass
import org.saar.core.renderer.renderpass.RenderPassContext

interface PostProcessor : RenderPass, ForwardRenderPass, DeferredRenderPass {

    fun render(context: RenderPassContext, buffers: PostProcessingBuffers)

    override fun render(context: RenderPassContext, buffers: ForwardRenderingBuffers) {
        render(context, PostProcessingBuffers(buffers.albedo))
    }

    override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        render(context, PostProcessingBuffers(buffers.albedo))
    }
}
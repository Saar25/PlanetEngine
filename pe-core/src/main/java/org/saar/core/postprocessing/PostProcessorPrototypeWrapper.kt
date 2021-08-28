package org.saar.core.postprocessing

import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.forward.ForwardRenderPass
import org.saar.core.renderer.forward.ForwardRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper

abstract class PostProcessorPrototypeWrapper(prototype: PostProcessorPrototype) :
    PostProcessor, ForwardRenderPass, DeferredRenderPass, RenderPassPrototypeWrapper<PostProcessingBuffers>(prototype) {

    override fun render(context: RenderPassContext, buffers: PostProcessingBuffers) {
        super.render(context, buffers)
    }

    override fun render(context: RenderPassContext, buffers: ForwardRenderingBuffers) {
        super.render(context, PostProcessingBuffers(buffers.albedo))
    }

    override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        super.render(context, PostProcessingBuffers(buffers.albedo))
    }
}
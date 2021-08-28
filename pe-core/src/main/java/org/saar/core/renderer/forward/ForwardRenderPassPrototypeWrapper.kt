package org.saar.core.renderer.forward

import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper

abstract class ForwardRenderPassPrototypeWrapper(prototype: RenderPassPrototype<ForwardRenderingBuffers>) :
    ForwardRenderPass, DeferredRenderPass, RenderPassPrototypeWrapper<ForwardRenderingBuffers>(prototype) {

    final override fun render(context: RenderPassContext, buffers: ForwardRenderingBuffers) {
        super.render(context, buffers)
    }

    final override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        super.render(context, ForwardRenderingBuffers(buffers.albedo, buffers.depth))
    }
}
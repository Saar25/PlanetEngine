package org.saar.core.renderer.deferred

import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper

abstract class DeferredRenderPassPrototypeWrapper(prototype: RenderPassPrototype<DeferredRenderingBuffers>) :
    DeferredRenderPass, RenderPassPrototypeWrapper<DeferredRenderingBuffers>(prototype) {

    final override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        super.render(context, buffers)
    }
}
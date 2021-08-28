package org.saar.core.postprocessing

import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper

abstract class PostProcessorPrototypeWrapper(prototype: PostProcessorPrototype) :
    PostProcessor, RenderPassPrototypeWrapper<PostProcessingBuffers>(prototype) {

    override fun render(context: RenderPassContext, buffers: PostProcessingBuffers) {
        super.render(context, buffers)
    }
}
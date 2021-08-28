package org.saar.core.postprocessing

import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassesPipelineHelper

class PostProcessingPipeline(vararg processors: PostProcessor) {

    private val helper = RenderPassesPipelineHelper(processors)

    fun process(context: RenderPassContext, buffers: PostProcessingBuffers) {
        this.helper.process { it.render(context, buffers) }
    }

    fun delete() = this.helper.delete()

}
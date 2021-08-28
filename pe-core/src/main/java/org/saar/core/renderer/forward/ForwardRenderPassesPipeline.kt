package org.saar.core.renderer.forward

import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassesPipelineHelper

class ForwardRenderPassesPipeline(vararg renderPasses: ForwardRenderPass) {

    private val helper = RenderPassesPipelineHelper(renderPasses)

    fun process(context: RenderPassContext, buffers: ForwardRenderingBuffers) {
        this.helper.process { it.render(context, buffers) }
    }

    fun delete() = this.helper.delete()
}
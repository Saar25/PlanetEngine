package org.saar.core.renderer.deferred

import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassesPipelineHelper

class DeferredRenderPassesPipeline(vararg renderPasses: DeferredRenderPass) {

    private val helper = RenderPassesPipelineHelper(renderPasses)

    fun prepare(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        this.helper.process { it.prepare(context, buffers) }
    }

    fun process(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        this.helper.process { it.render(context, buffers) }
    }

    fun delete() = this.helper.delete()
}
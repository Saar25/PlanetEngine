package org.saar.core.renderer.deferred

import org.saar.core.renderer.renderpass.RenderPass
import org.saar.core.renderer.renderpass.RenderPassContext

interface DeferredRenderPass : RenderPass {
    fun prepare(context: RenderPassContext, buffers: DeferredRenderingBuffers) = Unit
    fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers)
}
package org.saar.core.shadertoy

import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.postprocessing.PostProcessor
import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.forward.ForwardRenderPass
import org.saar.core.renderer.forward.ForwardRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPass
import org.saar.core.renderer.renderpass.RenderPassContext

interface ShaderToy : RenderPass, PostProcessor, ForwardRenderPass, DeferredRenderPass {

    fun render()

    override fun render(context: RenderPassContext, buffers: PostProcessingBuffers) = render()

    override fun render(context: RenderPassContext, buffers: ForwardRenderingBuffers) = render()

    override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) = render()
}
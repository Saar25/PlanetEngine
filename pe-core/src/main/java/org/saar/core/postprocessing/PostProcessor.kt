package org.saar.core.postprocessing

import org.saar.core.renderer.renderpass.RenderPass
import org.saar.core.renderer.renderpass.RenderPassContext

interface PostProcessor : RenderPass {

    fun render(context: RenderPassContext, buffers: PostProcessingBuffers)

}
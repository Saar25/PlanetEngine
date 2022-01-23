package org.saar.core.renderer.forward

import org.saar.core.renderer.RenderingPathPipeline

class ForwardRenderingPipeline(
    override vararg val passes: ForwardRenderPass,
) : RenderingPathPipeline<ForwardRenderingBuffers>
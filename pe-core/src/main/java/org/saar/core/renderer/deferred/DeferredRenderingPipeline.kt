package org.saar.core.renderer.deferred

import org.saar.core.renderer.RenderingPathPipeline

class DeferredRenderingPipeline(
    override vararg val passes: DeferredRenderPass,
) : RenderingPathPipeline<DeferredRenderingBuffers>
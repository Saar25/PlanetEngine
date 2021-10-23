package org.saar.core.renderer.p2d

import org.saar.core.renderer.RenderingPathPipeline

class RenderingPipeline2D(
    override vararg val passes: RenderPass2D
) : RenderingPathPipeline<RenderingBuffers2D> {
    override val prototype = ScreenPrototype2D()
}
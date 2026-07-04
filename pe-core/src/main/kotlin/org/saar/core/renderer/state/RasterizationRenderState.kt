package org.saar.core.renderer.state

import org.saar.rhi.opengl.resterization.toOpengl
import org.saar.rhi.resterization.RasterizationState

class RasterizationRenderState(rasterizationState: RasterizationState) : RenderState {

    private val openglRasterizationState = rasterizationState.toOpengl()

    override fun apply() = this.openglRasterizationState.set()
}
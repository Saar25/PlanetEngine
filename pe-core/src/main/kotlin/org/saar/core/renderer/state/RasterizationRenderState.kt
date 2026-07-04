package org.saar.core.renderer.state

import org.saar.rhi.opengl.rasterization.toOpengl
import org.saar.rhi.rasterization.RasterizationState

class RasterizationRenderState(rasterizationState: RasterizationState) : RenderState {

    private val openglRasterizationState = rasterizationState.toOpengl()

    override fun apply() = this.openglRasterizationState.set()
}
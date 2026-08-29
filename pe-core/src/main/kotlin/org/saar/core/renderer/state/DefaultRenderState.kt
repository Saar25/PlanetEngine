package org.saar.core.renderer.state

import org.saar.rhi.blending.BlendState
import org.saar.rhi.depthstencil.DepthStencilState
import org.saar.rhi.rasterization.RasterizationState

object DefaultRenderState : RenderState {

    val rasterizationRenderState = RasterizationRenderState(RasterizationState())
    val depthStencilRenderState = DepthStencilRenderState(DepthStencilState())
    val blendRenderState = BlendRenderState(BlendState())

    override fun apply() {
        this.rasterizationRenderState.apply()
        this.depthStencilRenderState.apply()
        this.blendRenderState.apply()
    }
}
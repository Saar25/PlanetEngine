package org.saar.core.renderer.state

import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.stencil.StencilTest
import org.saar.rhi.blending.BlendState
import org.saar.rhi.rasterization.RasterizationState

object DefaultRenderState : RenderState {

    val rasterizationRenderState = RasterizationRenderState(RasterizationState())
    val blendRenderState = BlendRenderState(BlendState())

    override fun apply() {
        DepthTest.apply(DepthState.DEFAULTS)
        StencilTest.apply(StencilState.DEFAULTS)
        this.rasterizationRenderState.apply()
        this.blendRenderState.apply()
    }
}
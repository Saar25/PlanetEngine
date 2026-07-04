package org.saar.core.renderer.state

import org.saar.lwjgl.opengl.blend.BlendState
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.stencil.StencilTest
import org.saar.rhi.resterization.RasterizationState

object DefaultRenderState : RenderState {

    val rasterizationRenderState = RasterizationRenderState(RasterizationState())

    override fun apply() {
        DepthTest.apply(DepthState.DEFAULTS)
        BlendTest.apply(BlendState.DEFAULTS)
        StencilTest.apply(StencilState.DEFAULTS)
        this.rasterizationRenderState.apply()
    }
}
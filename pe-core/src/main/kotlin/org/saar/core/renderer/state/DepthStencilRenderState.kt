package org.saar.core.renderer.state

import org.saar.rhi.depthstencil.DepthStencilState
import org.saar.rhi.opengl.depthstencil.toOpengl

class DepthStencilRenderState(depthStencilState: DepthStencilState) : RenderState {

    private val openglDepthStencilState = depthStencilState.toOpengl()

    override fun apply() = this.openglDepthStencilState.set()
}
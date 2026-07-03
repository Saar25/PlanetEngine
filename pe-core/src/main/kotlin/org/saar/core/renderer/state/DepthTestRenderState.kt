package org.saar.core.renderer.state

import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.depth.DepthTest

class DepthTestRenderState(private val depthState: DepthState) : RenderState {
    override fun apply() = DepthTest.apply(this.depthState)
}
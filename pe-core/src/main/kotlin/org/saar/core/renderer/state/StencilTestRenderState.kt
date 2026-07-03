package org.saar.core.renderer.state

import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.stencil.StencilTest

class StencilTestRenderState(private val stencilState: StencilState) : RenderState {
    override fun apply() = StencilTest.apply(this.stencilState)
}
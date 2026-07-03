package org.saar.core.renderer.state

import org.saar.lwjgl.opengl.blend.BlendState
import org.saar.lwjgl.opengl.blend.BlendTest

class BlendTestRenderState(private val blendState: BlendState) : RenderState {
    override fun apply() = BlendTest.apply(this.blendState)
}
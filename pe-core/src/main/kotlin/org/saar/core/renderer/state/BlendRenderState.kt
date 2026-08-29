package org.saar.core.renderer.state

import org.saar.rhi.blending.BlendState
import org.saar.rhi.opengl.blending.toOpengl

class BlendRenderState(blendState: BlendState) : RenderState {

    private val openglBlendState = blendState.toOpengl()

    override fun apply() = this.openglBlendState.set()
}
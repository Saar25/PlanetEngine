package org.saar.core.renderer.state

import org.saar.lwjgl.opengl.blend.BlendState
import org.saar.lwjgl.opengl.cullface.CullFaceState
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.stencil.StencilState

class MutableRenderState(
    var depthTest: DepthTestRenderState = DepthTestRenderState(DepthState.DEFAULTS),
    var blendTest: BlendTestRenderState = BlendTestRenderState(BlendState.DEFAULTS),
    var stencilTest: StencilTestRenderState = StencilTestRenderState(StencilState.DEFAULTS),
    var cullFace: CullFaceRenderState = CullFaceRenderState(CullFaceState.DEFAULTS),
) : RenderState {

    override fun apply() {
        this.blendTest.apply()
        this.depthTest.apply()
        this.stencilTest.apply()
        this.cullFace.apply()
    }
}
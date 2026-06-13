package org.saar.core.renderer.state

import org.saar.lwjgl.opengl.blend.BlendState
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.cullface.CullFace
import org.saar.lwjgl.opengl.cullface.CullFaceState
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.provokingvertex.ProvokingVertex
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.stencil.StencilTest

object DefaultRenderState : RenderState {

    override fun apply() {
        DepthTest.apply(DepthState.DEFAULTS)
        BlendTest.apply(BlendState.DEFAULTS)
        StencilTest.apply(StencilState.DEFAULTS)
        CullFace.set(CullFaceState.DEFAULTS)
        ProvokingVertex.set(ProvokingVertex.DEFAULTS)
    }
}
package org.saar.core.renderer.state

import org.saar.lwjgl.opengl.cullface.CullFace
import org.saar.lwjgl.opengl.cullface.CullFaceState

class CullFaceRenderState(private val cullFace: CullFaceState) : RenderState {
    override fun apply() = CullFace.set(this.cullFace)
}
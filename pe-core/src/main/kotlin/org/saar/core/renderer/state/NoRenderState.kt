package org.saar.core.renderer.state

object NoRenderState : RenderState {
    override fun apply() = Unit
}
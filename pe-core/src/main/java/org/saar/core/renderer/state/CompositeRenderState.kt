package org.saar.core.renderer.state

class CompositeRenderState(private val renderStates: Iterable<RenderState>) : RenderState {
    override fun apply() = this.renderStates.forEach(RenderState::apply)
}
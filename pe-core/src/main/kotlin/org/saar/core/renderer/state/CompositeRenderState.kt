package org.saar.core.renderer.state

class CompositeRenderState(private val renderStates: Iterable<RenderState>) : RenderState {
    constructor(vararg renderStates: RenderState) : this(renderStates.asIterable())

    override fun apply() = this.renderStates.forEach(RenderState::apply)
}
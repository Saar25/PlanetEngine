package org.saar.core.renderer

import org.saar.core.renderer.state.DefaultRenderState
import org.saar.core.renderer.state.RenderState

class RenderPipeline(
    private val passes: Iterable<RenderPass>,
    private val renderState: RenderState = DefaultRenderState
) {

    constructor(vararg passes: RenderPass) : this(passes = passes.asIterable())

    fun render(context: RenderContext) {
        this.passes.forEach {
            this.renderState.apply()
            it.render(context)
        }
    }

    fun delete() = this.passes.forEach { it.delete() }
}
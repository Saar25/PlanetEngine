package org.saar.core.renderer

import org.saar.core.renderer.state.DefaultRenderState
import org.saar.core.renderer.state.RenderState

class RenderGraph(
    private val nodes: Iterable<RenderGraphNode>,
    private val renderState: RenderState = DefaultRenderState
) {

    constructor(vararg nodes: RenderGraphNode) : this(nodes = nodes.asIterable())

    fun render(context: RenderContext) {
        this.nodes.forEach {
            this.renderState.apply()
            it.render(context)
        }
    }

    fun delete() = this.nodes.forEach { it.delete() }
}
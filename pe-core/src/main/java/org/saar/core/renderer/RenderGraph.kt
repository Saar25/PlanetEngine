package org.saar.core.renderer

import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.DefaultRenderState
import org.saar.core.renderer.state.RenderState

fun renderGraph(width: Int, height: Int, block: RenderGraph.Builder.() -> Unit): RenderGraph {
    return RenderGraph.Builder(width, height).apply(block).build()
}

class RenderGraph(
    private val nodes: Iterable<RenderPass>,
    private val renderState: RenderState = DefaultRenderState
) {

    constructor(vararg nodes: RenderPass) : this(nodes = nodes.asIterable())

    fun render(context: RenderContext) {
        this.nodes.forEach {
            this.renderState.apply()
            it.render(context)
        }
    }

    fun delete() = this.nodes.forEach { it.delete() }

    class Builder(val width: Int, val height: Int) {

        private val nodes = mutableListOf<RenderPass>()
        private var renderState: RenderState = DefaultRenderState

        fun addPass(renderPass: RenderPass): Builder {
            this.nodes.add(renderPass)
            return this
        }

        fun setState(renderState: RenderState): Builder {
            this.renderState = renderState
            return this
        }

        fun addState(renderState: RenderState): Builder {
            this.renderState = CompositeRenderState(this.renderState, renderState)
            return this
        }

        fun build() = RenderGraph(this.nodes, this.renderState)
    }
}
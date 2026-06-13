package org.saar.core.renderer

import org.saar.core.screen.Screen

class RenderGraphNode(private val renderNode: RenderNode, private val to: Screen) {

    fun render(context: RenderContext) {
        this.to.setAsDraw()
        this.renderNode.renderState.apply()
        this.renderNode.render(context)
    }

    fun delete() {
        this.renderNode.delete()
    }
}

fun RenderNode.onto(screen: Screen): RenderGraphNode {
    return RenderGraphNode(this, screen)
}
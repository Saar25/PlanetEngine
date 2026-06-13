package org.saar.core.renderer

import org.saar.core.screen.Screen

class RenderGraphNode(private val renderPass: RenderPass, private val to: Screen) {

    fun render(context: RenderContext) {
        this.to.setAsDraw()
        this.renderPass.renderState.apply()
        this.renderPass.render(context)
    }

    fun delete() {
        this.renderPass.delete()
    }
}

fun RenderPass.onto(screen: Screen): RenderGraphNode {
    return RenderGraphNode(this, screen)
}
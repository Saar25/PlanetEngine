package org.saar.core.renderer

import org.saar.core.screen.Screen

class RenderPass(private val renderNode: RenderNode, private val to: Screen) {

    fun render(context: RenderContext) {
        this.to.setAsDraw()
        this.renderNode.render(context)
    }

    fun delete() {
        this.renderNode.delete()
    }
}

fun RenderNode.onto(screen: Screen): RenderPass {
    return RenderPass(this, screen)
}
package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MoveEvent

interface UIContainer : UINode {

    val children: List<UINode>

    override fun onMouseClickEvent(event: ClickEvent): Boolean {
        return this.children.asReversed().any { it.onMouseClickEvent(event) }
    }

    override fun onMouseMoveEvent(event: MoveEvent) {
        for (childUiContainer in this.children.asReversed()) {
            childUiContainer.onMouseMoveEvent(event)
        }
    }

    override fun render(context: RenderContext) {
        this.children.forEach { it.render(context) }
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}
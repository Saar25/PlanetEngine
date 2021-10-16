package org.saar.gui

import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MoveEvent

interface UIParentElement : UIElement {

    override fun onMouseClickEvent(event: ClickEvent): Boolean {
        return this.children.asReversed().any { it.onMouseClickEvent(event) }
    }

    override fun onMouseMoveEvent(event: MoveEvent) {
        for (childUiContainer in this.children.asReversed()) {
            childUiContainer.onMouseMoveEvent(event)
        }
    }

    override fun update() {
        this.children.forEach { it.update() }
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}
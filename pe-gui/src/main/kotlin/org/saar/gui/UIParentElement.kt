package org.saar.gui

import org.saar.lwjgl.glfw.input.keyboard.KeyEvent
import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MoveEvent

interface UIParentElement : UIElement {

    override fun onMouseClickEvent(event: ClickEvent): Boolean {
        return this.children.asReversed().any { it.onMouseClickEvent(event) }
    }

    override fun onMouseMoveEvent(event: MoveEvent) {
        for (child in this.children.asReversed()) {
            child.onMouseMoveEvent(event)
        }
    }

    override fun onKeyPressEvent(event: KeyEvent) {
        for (child in this.children.asReversed()) {
            child.onKeyPressEvent(event)
        }
    }

    override fun onKeyReleaseEvent(event: KeyEvent) {
        for (child in this.children.asReversed()) {
            child.onKeyReleaseEvent(event)
        }
    }

    override fun onKeyRepeatEvent(event: KeyEvent) {
        for (child in this.children.asReversed()) {
            child.onKeyRepeatEvent(event)
        }
    }

    override fun update() {
        this.children.forEach { it.update() }
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}
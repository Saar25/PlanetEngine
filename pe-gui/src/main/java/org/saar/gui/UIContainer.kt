package org.saar.gui

import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MoveEvent

interface UIContainer : UIElement {

    val uiComponents: List<UIComponent>

    val uiContainers: List<UIContainer>

    fun onMouseClickEvent(event: ClickEvent) {
        for (childUiComponent in this.uiComponents.asReversed()) {
            if (childUiComponent.onMouseClickEvent(event)) break
        }
        for (childUiContainer in this.uiContainers) {
            childUiContainer.onMouseClickEvent(event)
        }
    }

    fun onMouseMoveEvent(event: MoveEvent) {
        for (childUiComponent in this.uiComponents.asReversed()) {
            childUiComponent.onMouseMoveEvent(event)
        }
        for (childUiContainer in this.uiContainers) {
            childUiContainer.onMouseMoveEvent(event)
        }
    }

    fun delete() {
        this.uiContainers.forEach { it.delete() }
        this.uiComponents.forEach { it.delete() }
    }
}
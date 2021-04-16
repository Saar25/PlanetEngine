package org.saar.gui

import org.saar.gui.style.WindowStyle
import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MoveEvent
import org.saar.lwjgl.glfw.window.Window

class UIDisplay(private val window: Window) : UIContainer {

    override val style = WindowStyle(this.window)

    override val uiComponents = mutableListOf<UIComponent>()

    override val uiContainers = mutableListOf<UIContainer>()

    init {
        this.window.mouse.addClickListener { event -> fireEvent(this, event) }

        this.window.mouse.addMoveListener { event -> fireEvent(this, event) }
    }

    private fun fireEvent(container: UIContainer, event: ClickEvent) {
        for (childComponent in container.uiComponents.asReversed()) {
            if (childComponent.onMouseClickEvent(event)) break
        }
        for (childContainer in container.uiContainers) {
            fireEvent(childContainer, event)
        }
    }

    private fun fireEvent(container: UIContainer, event: MoveEvent) {
        for (childComponent in container.uiComponents.asReversed()) {
            childComponent.onMouseMoveEvent(event)
        }
        for (childContainer in container.uiContainers) {
            fireEvent(childContainer, event)
        }
    }

    fun add(uiComponent: UIComponent) {
        this.uiComponents.add(uiComponent)
        uiComponent.parent = this
    }

    fun add(uiContainer: UIChildContainer) {
        this.uiContainers.add(uiContainer)
        uiContainer.parent = this
    }
}
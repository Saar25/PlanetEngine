package org.saar.gui

import org.saar.gui.style.WindowStyle
import org.saar.lwjgl.glfw.window.Window

class UIDisplay(private val window: Window) : UIContainer {

    override val style = WindowStyle(this.window)

    override val uiComponents = mutableListOf<UIComponent>()

    override val uiContainers = mutableListOf<UIContainer>()

    init {
        this.window.mouse.addClickListener { event ->
            for (childUiComponent in this.uiComponents.asReversed()) {
                if (childUiComponent.onMouseClickEvent(event)) break
            }
            for (childUiContainer in this.uiContainers) {
                childUiContainer.onMouseClickEvent(event)
            }
        }

        this.window.mouse.addMoveListener { event ->
            for (childUiComponent in this.uiComponents.asReversed()) {
                childUiComponent.onMouseMoveEvent(event)
            }
            for (childUiContainer in this.uiContainers) {
                childUiContainer.onMouseMoveEvent(event)
            }
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
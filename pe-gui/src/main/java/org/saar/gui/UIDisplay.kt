package org.saar.gui

import org.saar.gui.style.WindowStyle
import org.saar.lwjgl.glfw.window.Window

class UIDisplay(private val window: Window) : UIElement {

    override val style = WindowStyle(this.window)

    val uiComponents = mutableListOf<UIComponent>()

    init {
        this.window.mouse.addClickListener { event ->
            for (uiComponent in this.uiComponents) {
                uiComponent.onMouseClickEvent(event)
            }
        }
        this.window.mouse.addMoveListener { event ->
            for (uiComponent in this.uiComponents) {
                uiComponent.onMouseMoveEvent(event)
            }
        }
    }

    fun add(uiComponent: UIComponent) {
        this.uiComponents.add(uiComponent)
        uiComponent.setParent(this)
    }
}
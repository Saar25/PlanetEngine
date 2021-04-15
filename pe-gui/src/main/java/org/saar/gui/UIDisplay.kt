package org.saar.gui

import org.saar.gui.position.WindowPositioner
import org.saar.gui.render.UIRenderer
import org.saar.gui.style.WindowStyle
import org.saar.lwjgl.glfw.window.Window

class UIDisplay(private val window: Window) : UIElement {

    private val style = WindowStyle

    override val positioner = WindowPositioner(this.window)

    private val uiComponents = mutableListOf<UIComponent>()

    private val renderer = UIRenderer()

    fun add(uiComponent: UIComponent) {
        this.uiComponents.add(uiComponent)
    }
}
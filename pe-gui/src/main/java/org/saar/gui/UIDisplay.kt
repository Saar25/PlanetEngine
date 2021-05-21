package org.saar.gui

import org.saar.gui.style.WindowStyle
import org.saar.lwjgl.glfw.window.Window

class UIDisplay(private val window: Window) : UIContainer {

    override val children = mutableListOf<UINode>()

    override val style = WindowStyle(this.window)

    init {
        this.window.mouse.addClickListener(this::onMouseClickEvent)

        this.window.mouse.addMoveListener(this::onMouseMoveEvent)
    }

    fun add(uiNode: UIChildNode) {
        this.children.add(uiNode)
        uiNode.parent = this
    }
}
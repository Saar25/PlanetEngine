package org.saar.gui

import org.saar.gui.block.UIBlock
import org.saar.gui.style.WindowStyle
import org.saar.lwjgl.glfw.window.Window

class UIDisplay(private val window: Window) : UIParentElement {

    override val children = mutableListOf<UIElement>()

    override val style = WindowStyle(this.window)

    override val uiBlock = UIBlock(this.style)

    init {
        this.window.mouse.addClickListener(this::onMouseClickEvent)
        this.window.mouse.addMoveListener(this::onMouseMoveEvent)

        this.window.keyboard.addKeyPressListener(this::onKeyPressEvent)
        this.window.keyboard.addKeyReleaseListener(this::onKeyReleaseEvent)
        this.window.keyboard.addKeyRepeatListener(this::onKeyRepeatEvent)
    }

    fun add(uiNode: UIChildElement) {
        this.children.add(uiNode)
        uiNode.parent = this
    }
}
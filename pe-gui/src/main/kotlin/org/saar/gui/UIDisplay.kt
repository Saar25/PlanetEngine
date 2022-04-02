package org.saar.gui

import org.saar.gui.style.WindowStyle
import org.saar.lwjgl.glfw.window.Window

class UIDisplay(private val window: Window) : UIMutableParent() {

    override val style = WindowStyle(this)

    val width get() = this.window.width

    val height get() = this.window.height

    init {
        this.window.mouse.addClickListener(this::onMouseClickEvent)
        this.window.mouse.addMoveListener(this::onMouseMoveEvent)

        this.window.keyboard.addKeyPressListener(this::onKeyPressEvent)
        this.window.keyboard.addKeyReleaseListener(this::onKeyReleaseEvent)
        this.window.keyboard.addKeyRepeatListener(this::onKeyRepeatEvent)
    }

    override fun contains(x: Int, y: Int) = true
}
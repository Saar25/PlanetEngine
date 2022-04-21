package org.saar.gui

import org.saar.gui.event.asKeyboardEvent
import org.saar.gui.event.asMouseEvent
import org.saar.gui.style.WindowStyle
import org.saar.lwjgl.glfw.window.Window

class UIDisplay(private val window: Window) : UIMutableParent {

    override val style = WindowStyle(this)

    override var activeElement: UINode = UINullNode

    val width get() = this.window.width

    val height get() = this.window.height

    private val _children = mutableListOf<UINode>()
    override val children: List<UINode> = this._children

    override fun add(uiNode: UIChildNode) {
        if (this._children.add(uiNode)) {
            uiNode.parent = this
        }
    }

    override fun remove(uiNode: UIChildNode) {
        if (this._children.remove(uiNode)) {
            uiNode.parent = UINullNode
        }
    }

    init {
        this.window.mouse.addClickListener { e ->
            if (e.isDown) onMousePressEvent(e.asMouseEvent())
            else onMouseReleaseEvent(e.asMouseEvent())
        }
        this.window.mouse.addMoveListener { e -> onMouseMoveEvent(e.asMouseEvent()) }

        this.window.keyboard.addKeyPressListener { e -> onKeyPressEvent(e.asKeyboardEvent()) }
        this.window.keyboard.addKeyReleaseListener { e -> onKeyReleaseEvent(e.asKeyboardEvent()) }
        this.window.keyboard.addKeyRepeatListener { e -> onKeyRepeatEvent(e.asKeyboardEvent()) }
    }

    override fun contains(x: Int, y: Int) = true
}
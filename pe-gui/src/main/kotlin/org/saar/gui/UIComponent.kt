package org.saar.gui

import org.saar.gui.event.KeyboardEvent
import org.saar.gui.event.MouseEvent
import org.saar.gui.event.asKeyboardEvent
import org.saar.gui.event.asMouseEvent
import org.saar.gui.style.ComponentStyle
import org.saar.lwjgl.glfw.input.keyboard.KeyEvent
import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MoveEvent

/**
 * This class represent a UI component
 * it contains multiple UIBlock object and handles user events
 */
abstract class UIComponent : UIChildNode, UIParentNode {

    override var parent: UIParentNode = UINullNode

    final override val style = ComponentStyle(this)

    override var activeElement: UINode
        get() = super.activeElement
        set(value) {
            super.activeElement = value
        }

    var isMouseHover = false
        private set

    var isMousePressed = false
        private set

    val isFocused: Boolean get() = this.activeElement == this

    override fun onKeyPressEvent(event: KeyEvent) {
        val e = event.asKeyboardEvent()
        if (this.isFocused) onKeyPress(e)
    }

    override fun onKeyReleaseEvent(event: KeyEvent) {
        val e = event.asKeyboardEvent()
        if (this.isFocused) onKeyRelease(e)
    }

    override fun onKeyRepeatEvent(event: KeyEvent) {
        val e = event.asKeyboardEvent()
        if (this.isFocused) onKeyRepeat(e)
    }

    final override fun onMouseMoveEvent(event: MoveEvent) {
        val e = event.asMouseEvent()
        val x = event.mouse.xPos
        val y = event.mouse.yPos

        val mouseInside = contains(x, y)

        if (mouseInside && !this.isMouseHover) {
            mouseEnter(e)
        } else if (!mouseInside && this.isMouseHover) {
            mouseExit(e)
        }

        if (mouseInside && !e.button.isPrimary) {
            mouseMove(e)
        } else if (e.button.isPrimary && this.isMousePressed) {
            mouseDrag(e)
        }
    }

    final override fun onMouseClickEvent(event: ClickEvent): Boolean {
        val e = event.asMouseEvent()
        if (event.isDown && this.isMouseHover) {
            mousePress(e)
            this.activeElement = this
            return true
        } else if (this.isMousePressed) {
            mouseRelease(e)
            this.activeElement = this
            return true
        }
        this.activeElement = UINullNode
        return false
    }

    private fun mousePress(event: MouseEvent) {
        this.isMousePressed = true
        onMousePress(event)
    }

    private fun mouseRelease(event: MouseEvent) {
        this.isMousePressed = false
        onMouseRelease(event)
    }

    private fun mouseEnter(event: MouseEvent) {
        this.isMouseHover = true
        onMouseEnter(event)
    }

    private fun mouseExit(event: MouseEvent) {
        this.isMouseHover = false
        onMouseExit(event)
    }

    private fun mouseMove(event: MouseEvent) = onMouseMove(event)

    private fun mouseDrag(event: MouseEvent) = onMouseDrag(event)

    /**
     * Invoked when a key has been pressed while the component in focused
     *
     * @param event the keyboard event
     */
    open fun onKeyPress(event: KeyboardEvent) = Unit

    /**
     * Invoked when a key has been released while the component in focused
     *
     * @param event the keyboard event
     */
    open fun onKeyRelease(event: KeyboardEvent) = Unit

    /**
     * Invoked when a key has been repeated while the component in focused
     *
     * @param event the keyboard event
     */
    open fun onKeyRepeat(event: KeyboardEvent) = Unit

    /**
     * Invoked when a mouse button has been pressed on a component
     *
     * @param event the mouse event
     */
    open fun onMousePress(event: MouseEvent) = Unit

    /**
     * Invoked when a mouse button has been released on the component
     *
     * @param event the mouse event
     */
    open fun onMouseRelease(event: MouseEvent) = Unit

    /**
     * Invoked when the mouse enters the component
     *
     * @param event the mouse event
     */
    open fun onMouseEnter(event: MouseEvent) = Unit

    /**
     * Invoked when the mouse exits the component
     *
     * @param event the mouse event
     */
    open fun onMouseExit(event: MouseEvent) = Unit

    /**
     * Invoked when the mouse move on the component
     *
     * @param event the mouse event
     */
    open fun onMouseMove(event: MouseEvent) = Unit

    /**
     * Invoked when the mouse drags on the component
     *
     * @param event the mouse event
     */
    open fun onMouseDrag(event: MouseEvent) = Unit
}
package org.saar.gui

import org.saar.gui.event.KeyboardEvent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.ComponentStyle

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

    override fun onKeyPressEvent(event: KeyboardEvent) {
        if (this.isFocused) onKeyPress(event)
    }

    override fun onKeyReleaseEvent(event: KeyboardEvent) {
        if (this.isFocused) onKeyRelease(event)
    }

    override fun onKeyRepeatEvent(event: KeyboardEvent) {
        if (this.isFocused) onKeyRepeat(event)
    }

    final override fun onMouseMoveEvent(event: MouseEvent) {
        val inside = contains(event.x, event.y)

        if (inside && !this.isMouseHover) {
            mouseEnter(event)
        } else if (!inside && this.isMouseHover) {
            mouseExit(event)
        }

        if (inside && !event.button.isPrimary) {
            mouseMove(event)
        } else if (event.button.isPrimary && this.isMousePressed) {
            mouseDrag(event)
        }
    }

    final override fun onMousePressEvent(event: MouseEvent): Boolean {
        if (this.isMouseHover) {
            this.isMousePressed = true
            this.activeElement = this
            onMousePress(event)
            return true
        }
        this.activeElement = UINullNode
        return false
    }

    final override fun onMouseReleaseEvent(event: MouseEvent): Boolean {
        if (this.isMousePressed) {
            this.isMousePressed = false
            this.activeElement = this
            onMouseRelease(event)
            return true
        }
        this.activeElement = UINullNode
        return false
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
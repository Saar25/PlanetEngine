package org.saar.gui

import org.saar.gui.event.KeyboardEvent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.ComponentStyle

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

    final override fun onKeyPressEvent(event: KeyboardEvent) {
        if (this.isFocused) onKeyPress(event)
        else this.activeElement.onKeyPressEvent(event)
    }

    final override fun onKeyReleaseEvent(event: KeyboardEvent) {
        if (this.isFocused) onKeyRelease(event)
        else this.activeElement.onKeyReleaseEvent(event)
    }

    final override fun onKeyRepeatEvent(event: KeyboardEvent) {
        if (this.isFocused) onKeyRepeat(event)
        else this.activeElement.onKeyRepeatEvent(event)
    }

    final override fun onMouseMoveEvent(event: MouseEvent) {
        val inside = contains(event.x, event.y)

        if (inside && !this.isMouseHover) {
            this.isMouseHover = true
            onMouseEnter(event)
        } else if (!inside && this.isMouseHover) {
            this.isMouseHover = false
            onMouseExit(event)
        }

        if (inside && !event.button.isPrimary) {
            onMouseMove(event)
        } else if (event.button.isPrimary && this.isMousePressed) {
            onMouseDrag(event)
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
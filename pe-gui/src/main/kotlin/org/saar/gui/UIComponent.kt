package org.saar.gui

import org.saar.gui.block.UIBlock
import org.saar.gui.event.MouseEvent
import org.saar.gui.event.asMouseEvent
import org.saar.gui.style.Style
import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MoveEvent

/**
 * This class represent a ui component
 * it contains multiple UIBlock object and handles user events
 */
abstract class UIComponent : UIChildElement {

    final override var parent: UIElement = UINullElement

    final override val style = Style(this)

    final override val uiBlock = UIBlock(this.style)

    var isMouseHover = false
        private set

    var isMousePressed = false
        private set

    final override fun update() = this.children.forEach { it.update() }

    final override fun delete() = this.children.forEach { it.delete() }

    final override fun onMouseMoveEvent(event: MoveEvent) {
        val e = event.asMouseEvent()
        val x = event.mouse.xPos
        val y = event.mouse.yPos

        val mouseInside = checkMouseInside(x, y)

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
            return true
        } else if (this.isMousePressed) {
            mouseRelease(e)
            return true
        }
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

    fun checkMouseInside(x: Int, y: Int): Boolean {
        return if (this.children.isNotEmpty()) {
            this.children.any { it.contains(x, y) }
        } else this.uiBlock.inTouch(x, y)
    }

    /**
     * Returns whether the ui component is currently selected
     *
     * @return true if selected, false otherwise
     */
    val isSelected: Boolean = false

    /**
     * Invoked when a mouse button has been pressed on a component
     *
     * @param event the mouse event
     */
    open fun onMousePress(event: MouseEvent) {}

    /**
     * Invoked when a mouse button has been released on the component
     *
     * @param event the mouse event
     */
    open fun onMouseRelease(event: MouseEvent) {}

    /**
     * Invoked when the mouse enters the component
     *
     * @param event the mouse event
     */
    open fun onMouseEnter(event: MouseEvent) {}

    /**
     * Invoked when the mouse exits the component
     *
     * @param event the mouse event
     */
    open fun onMouseExit(event: MouseEvent) {}

    /**
     * Invoked when the mouse move on the component
     *
     * @param event the mouse event
     */
    fun onMouseMove(event: MouseEvent) {}

    /**
     * Invoked when the mouse drags on the component
     *
     * @param event the mouse event
     */
    open fun onMouseDrag(event: MouseEvent) {}
}
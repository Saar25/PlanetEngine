package org.saar.gui

import org.saar.gui.event.KeyboardEvent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.ComponentStyle

abstract class UIComponent : UIChildNode, UIParentNode {

    override var parent: UIParentNode = UINullNode

    final override val style = ComponentStyle(this)


    final override val uiInputHelper = UIInputHelper(this)

    init {
        this.uiInputHelper.addMousePressEventListener(::onMousePress)
        this.uiInputHelper.addMouseReleaseEventListener(::onMouseRelease)

        this.uiInputHelper.addMouseEnterEventListener(::onMouseEnter)
        this.uiInputHelper.addMouseExitEventListener(::onMouseExit)

        this.uiInputHelper.addMouseMoveEventListener(::onMouseMove)
        this.uiInputHelper.addMouseDragEventListener(::onMouseDrag)

        this.uiInputHelper.addKeyPressEventListener(::onKeyPress)
        this.uiInputHelper.addKeyReleaseEventListener(::onKeyRelease)
        this.uiInputHelper.addKeyRepeatEventListener(::onKeyRepeat)
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
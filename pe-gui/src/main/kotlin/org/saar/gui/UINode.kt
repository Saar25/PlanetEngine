package org.saar.gui

import org.saar.core.node.Node
import org.saar.core.renderer.RenderPass
import org.saar.gui.event.KeyboardEvent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.Style

interface UINode : RenderPass, Node {

    val style: Style

    val uiInputHelper: UIInputHelper

    val isMouseOver get() = this.uiInputHelper.isMouseOver

    val isMousePressed get() = this.uiInputHelper.isMousePressed

    val isFocused: Boolean get() = this.uiInputHelper.isFocused

    fun contains(x: Int, y: Int): Boolean

    fun onMousePressEvent(event: MouseEvent) = false

    fun onMouseReleaseEvent(event: MouseEvent) = false

    fun onMouseMoveEvent(event: MouseEvent) = Unit

    fun onKeyPressEvent(event: KeyboardEvent) = Unit

    fun onKeyReleaseEvent(event: KeyboardEvent) = Unit

    fun onKeyRepeatEvent(event: KeyboardEvent) = Unit

    override fun delete() = Unit
}
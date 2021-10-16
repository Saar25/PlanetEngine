package org.saar.gui.event

import org.saar.lwjgl.glfw.input.Modifiers
import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MouseButton
import org.saar.lwjgl.glfw.input.mouse.MoveEvent
import org.saar.lwjgl.glfw.window.Window

class MouseEvent(
    val button: MouseButton = MouseButton.NONE,
    val modifiers: Modifiers = Modifiers(0),
    val x: Int,
    val y: Int,
    val xBefore: Int,
    val yBefore: Int,
) {
    val deltaX: Int get() = this.x - this.xBefore
    val deltaY: Int get() = this.y - this.yBefore
    val xOnScreen: Int get() = Window.current().x + this.x
    val yOnScreen: Int get() = Window.current().y + this.y
}

fun ClickEvent.asMouseEvent() = MouseEvent(
    button = this.button,
    modifiers = this.modifiers,
    x = this.mouse.xPos,
    y = this.mouse.xPos,
    xBefore = this.mouse.xPos,
    yBefore = this.mouse.yPos,
)

fun MoveEvent.asMouseEvent() = MouseEvent(
    button = if (this.mouse.isButtonDown(MouseButton.PRIMARY)) MouseButton.PRIMARY else MouseButton.NONE,
    x = this.x.after,
    y = this.y.after,
    xBefore = this.x.before,
    yBefore = this.y.before
)
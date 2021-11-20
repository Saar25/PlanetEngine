package org.saar.gui.event

import org.saar.lwjgl.glfw.input.Modifiers
import org.saar.lwjgl.glfw.input.keyboard.KeyEvent

class KeyboardEvent(
    val keyCode: Int,
    val modifiers: Modifiers = Modifiers(0),
)

fun KeyEvent.asKeyboardEvent() = KeyboardEvent(
    keyCode = this.keyCode,
    modifiers = this.modifiers,
)
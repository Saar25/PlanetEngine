package org.saar.gui.event

import org.saar.lwjgl.glfw.input.Modifiers
import org.saar.lwjgl.glfw.input.keyboard.KeyEvent

class KeyboardEvent(
    val code: Int,
    val key: Int,
    val modifiers: Modifiers = Modifiers(0),
)

fun KeyEvent.asKeyboardEvent() = KeyboardEvent(
    code = this.code,
    key = this.key,
    modifiers = this.modifiers,
)
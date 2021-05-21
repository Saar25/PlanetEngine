package org.saar.lwjgl.glfw.input.keyboard

import org.saar.lwjgl.glfw.event.Event

class KeyEvent(
    val keyCode: Int,
    val modifiers: Modifiers
) : Event()
package org.saar.lwjgl.glfw.input.keyboard

import org.saar.lwjgl.glfw.event.Event
import org.saar.lwjgl.glfw.input.Modifiers

class KeyEvent(
    val keyCode: Int,
    val modifiers: Modifiers
) : Event()
package org.saar.lwjgl.glfw.input.mouse

import org.saar.lwjgl.glfw.event.Event
import org.saar.lwjgl.glfw.input.Modifiers

class ClickEvent(
    val mouse: Mouse,
    val button: MouseButton,
    val isDown: Boolean,
    val modifiers: Modifiers,
) : Event()
package org.saar.lwjgl.glfw.input.mouse

import org.saar.lwjgl.glfw.event.Event
import org.saar.lwjgl.glfw.event.IntValueChange

class MoveEvent(
    val mouse: Mouse,
    val x: IntValueChange,
    val y: IntValueChange,
) : Event()
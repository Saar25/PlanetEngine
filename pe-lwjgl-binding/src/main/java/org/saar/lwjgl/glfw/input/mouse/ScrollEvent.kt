package org.saar.lwjgl.glfw.input.mouse

import org.saar.lwjgl.glfw.event.Event

class ScrollEvent(
    val mouse: Mouse,
    val offset: Double,
) : Event()
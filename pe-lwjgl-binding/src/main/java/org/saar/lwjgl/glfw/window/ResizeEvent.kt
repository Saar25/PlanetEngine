package org.saar.lwjgl.glfw.window

import org.saar.lwjgl.glfw.event.Event
import org.saar.lwjgl.glfw.event.IntValueChange

class ResizeEvent(val width: IntValueChange, val height: IntValueChange) : Event

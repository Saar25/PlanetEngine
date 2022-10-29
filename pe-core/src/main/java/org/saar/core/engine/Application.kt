package org.saar.core.engine

import org.saar.lwjgl.glfw.window.Window

interface Application {

    fun initialize(window: Window)

    fun update(window: Window)

    fun render(window: Window)

    fun close(window: Window)

}
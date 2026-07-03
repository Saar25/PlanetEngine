package org.saar.core.util

import org.lwjgl.glfw.GLFW

private fun current(): Double {
    return GLFW.glfwGetTime()
}

class Fps {
    private var last: Double = current()

    fun update() {
        this.last = current()
    }

    fun delta() = current() - this.last

    fun fps() = 1 / delta()
}

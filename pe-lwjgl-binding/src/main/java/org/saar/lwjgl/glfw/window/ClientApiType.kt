package org.saar.lwjgl.glfw.window

import org.lwjgl.glfw.GLFW

enum class ClientApiType(private val value: Int) {
    NO_API(GLFW.GLFW_NO_API),
    OPENGL_API(GLFW.GLFW_OPENGL_API),
    OPENGL_ES_API(GLFW.GLFW_OPENGL_ES_API),
    ;

    fun get() = this.value
}

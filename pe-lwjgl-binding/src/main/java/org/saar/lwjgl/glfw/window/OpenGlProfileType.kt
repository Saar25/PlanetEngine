package org.saar.lwjgl.glfw.window

import org.lwjgl.glfw.GLFW

enum class OpenGlProfileType(private val value: Int) {
    ANY(GLFW.GLFW_OPENGL_ANY_PROFILE),
    CORE(GLFW.GLFW_OPENGL_CORE_PROFILE),
    COMPATIBILITY(GLFW.GLFW_OPENGL_COMPAT_PROFILE),
    ;

    fun get() = this.value
}

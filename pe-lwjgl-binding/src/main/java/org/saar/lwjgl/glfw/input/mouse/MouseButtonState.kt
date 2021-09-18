package org.saar.lwjgl.glfw.input.mouse

import org.lwjgl.glfw.GLFW

enum class MouseButtonState(private val value: Int) {

    RELEASE(GLFW.GLFW_RELEASE),
    PRESS(GLFW.GLFW_PRESS),
    ;

    fun get() = this.value

    companion object {
        @JvmStatic
        fun valueOf(value: Int): MouseButtonState = when (value) {
            RELEASE.value -> RELEASE
            PRESS.value -> PRESS
            else -> throw IllegalArgumentException("MouseButtonState non found: $value")
        }
    }
}
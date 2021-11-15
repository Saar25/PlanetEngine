package org.saar.lwjgl.glfw.input.keyboard

import org.lwjgl.glfw.GLFW

enum class KeyState(private val value: Int) {

    RELEASE(GLFW.GLFW_RELEASE),
    PRESS(GLFW.GLFW_PRESS),
    REPEAT(GLFW.GLFW_REPEAT),
    ;

    fun get() = this.value

    companion object {
        @JvmStatic
        fun valueOf(value: Int) = when (value) {
            RELEASE.value -> RELEASE
            PRESS.value -> PRESS
            REPEAT.value -> REPEAT
            else -> throw IllegalArgumentException("KeyState non found: $value")
        }
    }
}
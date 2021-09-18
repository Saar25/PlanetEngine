package org.saar.lwjgl.glfw.input.mouse

import org.lwjgl.glfw.GLFW

enum class MouseCursor(private val value: Int) {

    NORMAL(GLFW.GLFW_CURSOR_NORMAL),
    HIDDEN(GLFW.GLFW_CURSOR_HIDDEN),
    DISABLED(GLFW.GLFW_CURSOR_DISABLED),
    ;

    fun get() = this.value

    companion object {
        @JvmStatic
        fun valueOf(value: Int) = when (value) {
            NORMAL.value -> NORMAL
            HIDDEN.value -> HIDDEN
            DISABLED.value -> DISABLED
            else -> throw IllegalArgumentException("MouseCursor non found: $value")
        }
    }
}
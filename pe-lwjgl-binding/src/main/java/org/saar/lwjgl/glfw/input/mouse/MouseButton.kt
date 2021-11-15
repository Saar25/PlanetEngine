package org.saar.lwjgl.glfw.input.mouse

import org.lwjgl.glfw.GLFW

enum class MouseButton(private val value: Int) {
    BTN_1(GLFW.GLFW_MOUSE_BUTTON_1), // PRIMARY
    BTN_2(GLFW.GLFW_MOUSE_BUTTON_2), // SECONDARY
    BTN_3(GLFW.GLFW_MOUSE_BUTTON_3), // MIDDLE
    BTN_4(GLFW.GLFW_MOUSE_BUTTON_4),
    BTN_5(GLFW.GLFW_MOUSE_BUTTON_5),
    BTN_6(GLFW.GLFW_MOUSE_BUTTON_6),
    BTN_7(GLFW.GLFW_MOUSE_BUTTON_7),
    BTN_8(GLFW.GLFW_MOUSE_BUTTON_8), // LAST
    NONE(0),
    ;

    fun get() = this.value

    val isPrimary get() = this == PRIMARY
    val isSecondary get() = this == SECONDARY

    companion object {

        @JvmStatic
        val PRIMARY = BTN_1

        @JvmStatic
        val SECONDARY = BTN_2

        @JvmStatic
        val MIDDLE = BTN_3

        @JvmStatic
        val LAST = BTN_8

        @JvmStatic
        fun valueOf(value: Int) = when (value) {
            BTN_1.value -> BTN_1
            BTN_2.value -> BTN_2
            BTN_3.value -> BTN_3
            BTN_4.value -> BTN_4
            BTN_5.value -> BTN_5
            BTN_6.value -> BTN_6
            BTN_7.value -> BTN_7
            BTN_8.value -> BTN_8
            else -> throw IllegalArgumentException("MouseButton non found: $value");
        }
    }
}
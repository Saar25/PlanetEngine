package org.saar.lwjgl.glfw.input.keyboard

import org.lwjgl.glfw.GLFW
import org.saar.lwjgl.glfw.input.Modifiers

object KeyMapper {

    private val shiftMap = mapOf(
        96 to '~', 49 to '!', 50 to '@',
        51 to '#', 52 to '$', 53 to '%',
        54 to '^', 55 to '&', 56 to '*',
        57 to '(', 48 to ')', 45 to '_',
        61 to '+', 91 to '{', 93 to '}',
        92 to '|', 59 to ':', 222 to '\\',
        44 to '<', 46 to '>', 47 to '?',
        32 to ' ', 39 to '"'
    ).mapValues { it.value.code }

    private val numLockMap = mapOf(
        GLFW.GLFW_KEY_KP_0 to GLFW.GLFW_KEY_INSERT,
        GLFW.GLFW_KEY_KP_1 to GLFW.GLFW_KEY_END,
        GLFW.GLFW_KEY_KP_2 to GLFW.GLFW_KEY_DOWN,
        GLFW.GLFW_KEY_KP_3 to GLFW.GLFW_KEY_PAGE_DOWN,
        GLFW.GLFW_KEY_KP_4 to GLFW.GLFW_KEY_LEFT,
        GLFW.GLFW_KEY_KP_6 to GLFW.GLFW_KEY_RIGHT,
        GLFW.GLFW_KEY_KP_7 to GLFW.GLFW_KEY_HOME,
        GLFW.GLFW_KEY_KP_8 to GLFW.GLFW_KEY_UP,
        GLFW.GLFW_KEY_KP_9 to GLFW.GLFW_KEY_PAGE_UP,
    )

    @JvmStatic
    fun mapToKey(code: Int, modifiers: Modifiers): Int {
        return code.toChar().lowercaseChar().code
            .let { if (modifiers.isCapsLock()) it.toChar().uppercaseChar().code else it }
            .let { if (modifiers.isShift()) shiftMap.getOrDefault(it, it.toChar().uppercaseChar().code) else it }
            .let { if (modifiers.isNumLock()) numLockMap.getOrDefault(it, it) else it }
    }

}
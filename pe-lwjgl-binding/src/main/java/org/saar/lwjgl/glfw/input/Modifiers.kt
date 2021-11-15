package org.saar.lwjgl.glfw.input

import org.lwjgl.glfw.GLFW

class Modifiers(private val modifiers: Int) {

    fun isShift(): Boolean = checkBit(GLFW.GLFW_MOD_SHIFT)

    fun isCtrl(): Boolean = checkBit(GLFW.GLFW_MOD_CONTROL)

    fun isAlt(): Boolean = checkBit(GLFW.GLFW_MOD_ALT)

    fun isSuper(): Boolean = checkBit(GLFW.GLFW_MOD_SUPER)

    fun isCapsLock(): Boolean = checkBit(GLFW.GLFW_MOD_CAPS_LOCK)

    fun isNumLock(): Boolean = checkBit(GLFW.GLFW_MOD_NUM_LOCK)

    private fun checkBit(mask: Int) = this.modifiers and mask != 0

}
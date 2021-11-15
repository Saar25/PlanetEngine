package org.saar.lwjgl.glfw.window

import org.lwjgl.glfw.GLFW

private fun hintOf(type: WindowHintType, value: Int) = WindowHint { GLFW.glfwWindowHint(type.get(), value) }

private fun hintOf(type: WindowHintType, value: Boolean) = hintOf(type, if (value) 1 else 0)

private fun hintOf(vararg hints: WindowHint) = WindowHint { hints.forEach(WindowHint::apply) }

object WindowHints {

    @JvmStatic
    @JvmOverloads
    fun focused(value: Boolean = true) = hintOf(WindowHintType.FOCUSED, value)

    @JvmStatic
    @JvmOverloads
    fun iconified(value: Boolean = true) = hintOf(WindowHintType.ICONIFIED, value)

    @JvmStatic
    @JvmOverloads
    fun resizable(value: Boolean = true) = hintOf(WindowHintType.RESIZABLE, value)

    @JvmStatic
    @JvmOverloads
    fun visible(value: Boolean = true) = hintOf(WindowHintType.VISIBLE, value)

    @JvmStatic
    @JvmOverloads
    fun decorated(value: Boolean = true) = hintOf(WindowHintType.DECORATED, value)

    @JvmStatic
    @JvmOverloads
    fun autoIconify(value: Boolean = true) = hintOf(WindowHintType.AUTO_ICONIFY, value)

    @JvmStatic
    @JvmOverloads
    fun floating(value: Boolean = true) = hintOf(WindowHintType.FLOATING, value)

    @JvmStatic
    @JvmOverloads
    fun maximized(value: Boolean = true) = hintOf(WindowHintType.MAXIMIZED, value)

    @JvmStatic
    @JvmOverloads
    fun centerCursor(value: Boolean = true) = hintOf(WindowHintType.CENTER_CURSOR, value)

    @JvmStatic
    @JvmOverloads
    fun transparent(value: Boolean = true) = hintOf(WindowHintType.TRANSPARENT_FRAMEBUFFER, value)

    @JvmStatic
    @JvmOverloads
    fun hovered(value: Boolean = true) = hintOf(WindowHintType.HOVERED, value)

    @JvmStatic
    @JvmOverloads
    fun focusOnShow(value: Boolean = true) = hintOf(WindowHintType.FOCUS_ON_SHOW, value)

    @JvmStatic
    fun contextVersionMajor(value: Int) = hintOf(WindowHintType.CONTEXT_VERSION_MAJOR, value)

    @JvmStatic
    fun contextVersionMinor(value: Int) = hintOf(WindowHintType.CONTEXT_VERSION_MINOR, value)

    @JvmStatic
    fun contextVersion(major: Int, minor: Int) = hintOf(contextVersionMajor(major), contextVersionMinor(minor))

    @JvmStatic
    @JvmOverloads
    fun openglForwardCompatibility(value: Boolean = true) = hintOf(WindowHintType.OPENGL_FORWARD_COMPAT, value)

    @JvmStatic
    fun openglProfile(value: OpenGlProfileType) = hintOf(WindowHintType.OPENGL_PROFILE, value.get())
}
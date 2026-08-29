package org.saar.lwjgl.glfw.event

fun interface EventListener<in T : Event> {
    fun onEvent(e: T)
}

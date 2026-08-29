package org.saar.lwjgl.glfw.event

fun interface OnAction<T : Event> {
    fun perform(listener: EventListener<T>)
}

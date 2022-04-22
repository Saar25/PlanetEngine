package org.saar.gui.event

fun interface EventListener<T> {
    fun handle(event: T)
}

fun <T> EventListener<T>.andThen(listener: EventListener<in T>) = EventListener { event: T ->
    handle(event)
    listener.handle(event)
}
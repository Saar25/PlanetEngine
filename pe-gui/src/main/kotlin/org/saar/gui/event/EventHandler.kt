package org.saar.gui.event

fun interface EventHandler<T> {
    fun handle(event: T)
}

fun <T> EventHandler<T>.andThen(listener: EventHandler<in T>) = EventHandler { event: T ->
    handle(event)
    listener.handle(event)
}
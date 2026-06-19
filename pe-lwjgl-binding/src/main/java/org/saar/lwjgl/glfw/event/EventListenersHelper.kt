package org.saar.lwjgl.glfw.event

abstract class EventListenersHelper<T : Event> {
    abstract fun addListener(listener: EventListener<T>): EventListenersHelper<T>

    abstract fun removeListener(listener: EventListener<T>): EventListenersHelper<T>

    abstract fun fireEvent(event: T)

    private object Empty : EventListenersHelper<Event>() {
        override fun addListener(listener: EventListener<Event>) = Single(listener)

        override fun removeListener(listener: EventListener<Event>) = this

        override fun fireEvent(event: Event) = Unit
    }

    private class Single<T : Event>(private val listener: EventListener<T>) : EventListenersHelper<T>() {
        override fun addListener(listener: EventListener<T>) = Generic(this.listener, listener)

        override fun removeListener(listener: EventListener<T>): EventListenersHelper<T> {
            if (this.listener === listener) {
                return empty()
            }
            return this
        }

        override fun fireEvent(event: T) {
            this.listener.onEvent(event)
        }
    }

    private class Generic<T : Event>(vararg listeners: EventListener<T>) : EventListenersHelper<T>() {
        private val listeners: MutableList<EventListener<T>> = mutableListOf(*listeners)

        override fun addListener(listener: EventListener<T>): EventListenersHelper<T> {
            this.listeners.add(listener)
            return this
        }

        override fun removeListener(listener: EventListener<T>): EventListenersHelper<T> {
            this.listeners.remove(listener)

            return if (this.listeners.size == 1) Single(this.listeners[0]) else this
        }

        override fun fireEvent(event: T) {
            this.listeners.forEach { it.onEvent(event) }
        }
    }

    companion object {
        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T : Event> empty() = Empty as EventListenersHelper<T>
    }
}

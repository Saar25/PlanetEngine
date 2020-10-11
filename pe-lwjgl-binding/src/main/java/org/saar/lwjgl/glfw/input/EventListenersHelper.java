package org.saar.lwjgl.glfw.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class EventListenersHelper<T extends Event> {

    public abstract EventListenersHelper<T> addListener(EventListener<T> listener);

    public abstract EventListenersHelper<T> removeListener(EventListener<T> listener);

    public abstract void fireEvent(T event);

    public static <T extends Event> EventListenersHelper<T> empty() {
        @SuppressWarnings("unchecked") final EventListenersHelper<T> empty =
                (EventListenersHelper<T>) Empty.EMPTY;
        return empty;
    }

    private static class Empty<T extends Event> extends EventListenersHelper<T> {

        private static final EventListenersHelper<?> EMPTY = new Empty<>();

        @Override
        public EventListenersHelper<T> addListener(EventListener<T> listener) {
            return new Single<>(listener);
        }

        @Override
        public EventListenersHelper<T> removeListener(EventListener<T> listener) {
            return this;
        }

        @Override
        public void fireEvent(T event) {
        }
    }

    private static class Single<T extends Event> extends EventListenersHelper<T> {

        private final EventListener<T> listener;

        public Single(EventListener<T> listener) {
            this.listener = listener;
        }

        @Override
        public EventListenersHelper<T> addListener(EventListener<T> listener) {
            return new Generic<>(this.listener, listener);
        }

        @Override
        public EventListenersHelper<T> removeListener(EventListener<T> listener) {
            if (this.listener == listener) {
                return EventListenersHelper.empty();
            }
            return this;
        }

        @Override
        public void fireEvent(T event) {
            this.listener.onEvent(event);
        }
    }

    private static class Generic<T extends Event> extends EventListenersHelper<T> {

        private final List<EventListener<T>> listeners;

        @SafeVarargs
        public Generic(EventListener<T>... listeners) {
            this.listeners = new ArrayList<>(Arrays.asList(listeners));
        }

        @Override
        public EventListenersHelper<T> addListener(EventListener<T> listener) {
            this.listeners.add(listener);
            return this;
        }

        @Override
        public EventListenersHelper<T> removeListener(EventListener<T> listener) {
            if (this.listeners.contains(listener)) {
                if (this.listeners.size() == 2) {
                    if (this.listeners.get(0) == listener) {
                        return new Single<>(this.listeners.get(1));
                    } else {
                        return new Single<>(this.listeners.get(0));
                    }
                }
                this.listeners.remove(listener);
            }
            return this;
        }

        @Override
        public void fireEvent(T event) {
            for (EventListener<T> listener : this.listeners) {
                listener.onEvent(event);
            }
        }
    }

}

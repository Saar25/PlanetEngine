package org.saar.utils.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ListenersHelper<T> {

    public static <T> ListenersHelper<T> empty() {
        return new Empty<>();
    }

    public abstract ListenersHelper<T> addListener(ChangeListener<T> listener);

    public abstract ListenersHelper<T> removeListener(ChangeListener<T> listener);

    public abstract void fireEvent(ChangeEvent<T> event);

    private static class Empty<T> extends ListenersHelper<T> {

        @Override
        public ListenersHelper<T> addListener(ChangeListener<T> listener) {
            return listener == null ? this : new SingleChange<>(listener);
        }

        @Override
        public ListenersHelper<T> removeListener(ChangeListener<T> listener) {
            return this;
        }

        @Override
        public void fireEvent(ChangeEvent<T> event) {

        }
    }

    private static class SingleChange<T> extends ListenersHelper<T> {

        private final ChangeListener<T> listener;

        public SingleChange(ChangeListener<T> listener) {
            this.listener = listener;
        }

        @Override
        public ListenersHelper<T> addListener(ChangeListener<T> listener) {
            return listener == null ? this : new Generic<>(
                    Arrays.asList(this.listener, listener));
        }

        @Override
        public ListenersHelper<T> removeListener(ChangeListener<T> listener) {
            return this.listener != listener ? this : new Empty<>();
        }

        @Override
        public void fireEvent(ChangeEvent<T> event) {
            this.listener.onChange(event);
        }
    }

    private static class Generic<T> extends ListenersHelper<T> {

        private final List<ChangeListener<T>> listeners;

        public Generic(List<ChangeListener<T>> listeners) {
            this.listeners = listeners;
        }

        @Override
        public ListenersHelper<T> addListener(ChangeListener<T> listener) {
            if (listener == null) return this;
            final List<ChangeListener<T>> listeners =
                    new ArrayList<>(this.listeners);
            listeners.add(listener);

            return new Generic<T>(listeners);
        }

        @Override
        public ListenersHelper<T> removeListener(ChangeListener<T> listener) {
            if (listener == null || !this.listeners.contains(listener)) {
                return this;
            }
            final List<ChangeListener<T>> listeners =
                    new ArrayList<>(this.listeners);
            listeners.remove(listener);

            return new Generic<T>(listeners);
        }

        @Override
        public void fireEvent(ChangeEvent<T> event) {
            for (ChangeListener<T> listener : this.listeners) {
                listener.onChange(event);
            }
        }
    }

}

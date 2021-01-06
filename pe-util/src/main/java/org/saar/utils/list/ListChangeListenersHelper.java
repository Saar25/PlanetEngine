package org.saar.utils.list;

import java.util.ArrayList;
import java.util.List;

public abstract class ListChangeListenersHelper<T> {

    @SuppressWarnings("unchecked")
    public static <T> ListChangeListenersHelper<T> empty() {
        return (Empty<T>) Empty.empty;
    }

    public abstract ListChangeListenersHelper<T> add(ListChangeListener<T> listener);

    public abstract ListChangeListenersHelper<T> remove(ListChangeListener<T> listener);

    public abstract void fireEvent(ListChangeEvent<T> event);

    private static class Empty<T> extends ListChangeListenersHelper<T> {

        public static final ListChangeListenersHelper<?> empty = new Empty<>();

        @Override
        public ListChangeListenersHelper<T> add(ListChangeListener<T> listener) {
            return new Single<>(listener);
        }

        @Override
        public ListChangeListenersHelper<T> remove(ListChangeListener<T> listener) {
            return this;
        }

        @Override
        public void fireEvent(ListChangeEvent<T> event) {
        }
    }

    private static class Single<T> extends ListChangeListenersHelper<T> {

        private final ListChangeListener<T> listener;

        public Single(ListChangeListener<T> listener) {
            this.listener = listener;
        }

        @Override
        public ListChangeListenersHelper<T> add(ListChangeListener<T> listener) {
            return new Generic<>(this.listener, listener);
        }

        @Override
        public ListChangeListenersHelper<T> remove(ListChangeListener<T> listener) {
            return this.listener == listener ? empty() : this;
        }

        @Override
        public void fireEvent(ListChangeEvent<T> event) {
            this.listener.changed(event);
        }
    }

    private static class Generic<T> extends ListChangeListenersHelper<T> {

        private final List<ListChangeListener<T>> listeners = new ArrayList<>();

        public Generic(ListChangeListener<T> a, ListChangeListener<T> b) {
            this.listeners.add(a);
            this.listeners.add(b);
        }

        @Override
        public ListChangeListenersHelper<T> add(ListChangeListener<T> listener) {
            this.listeners.add(listener);
            return this;
        }

        @Override
        public ListChangeListenersHelper<T> remove(ListChangeListener<T> listener) {
            if (this.listeners.remove(listener) && this.listeners.size() == 1) {
                return new Single<>(this.listeners.get(0));
            }
            return this;
        }

        @Override
        public void fireEvent(ListChangeEvent<T> event) {
            for (ListChangeListener<T> listener : this.listeners) {
                listener.changed(event);
            }
        }
    }
}

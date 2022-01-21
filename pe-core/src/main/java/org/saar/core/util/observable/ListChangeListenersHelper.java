package org.saar.core.util.observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class ListChangeListenersHelper<T> {

    @SuppressWarnings("unchecked")
    public static <T> ListChangeListenersHelper<T> empty() {
        return (ListChangeListenersHelper<T>) Empty.INSTANCE;
    }

    public abstract ListChangeListenersHelper<T> addListener(ListChangeListener<? super T> listener);

    public abstract ListChangeListenersHelper<T> removeListener(ListChangeListener<? super T> listener);

    public abstract void fireEvent(ObservableList<T> observable, Collection<T> added, Collection<T> removed);

    private static class Empty<T> extends ListChangeListenersHelper<T> {

        private static final Empty<?> INSTANCE = new Empty<>();

        @Override
        public ListChangeListenersHelper<T> addListener(ListChangeListener<? super T> listener) {
            return listener == null ? this : new ListChangeListenersHelper.SingleChange<>(listener);
        }

        @Override
        public ListChangeListenersHelper<T> removeListener(ListChangeListener<? super T> listener) {
            return this;
        }

        @Override
        public void fireEvent(ObservableList<T> observable, Collection<T> added, Collection<T> removed) {

        }
    }

    private static class SingleChange<T> extends ListChangeListenersHelper<T> {

        private final ListChangeListener<? super T> listener;

        public SingleChange(ListChangeListener<? super T> listener) {
            this.listener = listener;
        }

        @Override
        public ListChangeListenersHelper<T> addListener(ListChangeListener<? super T> listener) {
            return listener == null ? this : new ListChangeListenersHelper.Generic<>(
                    Arrays.asList(this.listener, listener));
        }

        @Override
        public ListChangeListenersHelper<T> removeListener(ListChangeListener<? super T> listener) {
            return this.listener != listener ? this : empty();
        }

        @Override
        public void fireEvent(ObservableList<T> observable, Collection<T> added, Collection<T> removed) {
            final ListChangeEvent<T> event = new ListChangeEvent<>(observable, added, removed);
            this.listener.onChange(event);
        }
    }

    private static class Generic<T> extends ListChangeListenersHelper<T> {

        private final List<ListChangeListener<? super T>> listeners;

        public Generic(List<ListChangeListener<? super T>> listeners) {
            this.listeners = listeners;
        }

        @Override
        public ListChangeListenersHelper<T> addListener(ListChangeListener<? super T> listener) {
            if (listener == null) return this;
            final List<ListChangeListener<? super T>> listeners =
                    new ArrayList<>(this.listeners);
            listeners.add(listener);

            return new ListChangeListenersHelper.Generic<>(listeners);
        }

        @Override
        public ListChangeListenersHelper<T> removeListener(ListChangeListener<? super T> listener) {
            if (listener == null || !this.listeners.contains(listener)) {
                return this;
            }
            final List<ListChangeListener<? super T>> listeners =
                    new ArrayList<>(this.listeners);
            listeners.remove(listener);

            return new ListChangeListenersHelper.Generic<>(listeners);
        }

        @Override
        public void fireEvent(ObservableList<T> observable, Collection<T> added, Collection<T> removed) {
            final ListChangeEvent<T> event = new ListChangeEvent<>(observable, added, removed);

            for (ListChangeListener<? super T> listener : this.listeners) {
                listener.onChange(event);
            }
        }
    }

}

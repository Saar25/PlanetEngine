package org.saar.core.util.observable;

import org.jproperty.InvalidationListener;

import java.util.*;

public final class ObservableLists {

    private ObservableLists() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    @SuppressWarnings("unchecked")
    public static <T> ObservableList<T> emptyObservableList() {
        return (ObservableList<T>) ObservableListWrapper.EMPTY;
    }

    public static <T> ObservableList<T> observableListOf() {
        return emptyObservableList();
    }

    @SafeVarargs
    public static <T> ObservableList<T> observableListOf(T... elements) {
        return new ObservableListWrapper<>(new ArrayList<>(Arrays.asList(elements)));
    }

    public static <T> ObservableList<T> mutableObservableListOf() {
        return new MutableObservableListWrapper<>(new ArrayList<>());
    }

    @SafeVarargs
    public static <T> ObservableList<T> mutableObservableListOf(T... elements) {
        return new MutableObservableListWrapper<>(new ArrayList<>(Arrays.asList(elements)));
    }

    public static <T> ObservableList<T> wrapOf(List<T> list) {
        return new ObservableListWrapper<>(list);
    }

    public static <T> ObservableList<T> mutableWrapOf(List<T> list) {
        return new MutableObservableListWrapper<>(list);
    }

    private static class ObservableListWrapper<T> extends AbstractList<T> implements ObservableList<T> {

        public static final ObservableListWrapper<?> EMPTY = new ObservableListWrapper<>(Collections.emptyList());

        private final List<T> list;

        public ObservableListWrapper(List<T> list) {
            this.list = list;
        }

        @Override
        public void addListener(ListChangeListener<T> listener) {

        }

        @Override
        public void removeListener(ListChangeListener<T> listener) {

        }

        @Override
        public void addListener(InvalidationListener listener) {

        }

        @Override
        public void removeListener(InvalidationListener listener) {

        }

        @Override
        public int size() {
            return this.list.size();
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public boolean contains(Object element) {
            return this.list.contains(element);
        }

        @Override
        public Iterator<T> iterator() {
            return this.list.iterator();
        }

        @Override
        public Object[] toArray() {
            return this.list.toArray();
        }

        @Override
        public <T1> T1[] toArray(T1[] array) {
            return this.list.toArray(array);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.list.containsAll(collection);
        }

        @Override
        public T get(int index) {
            return this.list.get(index);
        }

        @Override
        public int indexOf(Object element) {
            return this.list.indexOf(element);
        }

        @Override
        public int lastIndexOf(Object element) {
            return this.list.lastIndexOf(element);
        }

        @Override
        public ListIterator<T> listIterator() {
            return this.list.listIterator();
        }

        @Override
        public ListIterator<T> listIterator(int from) {
            return this.list.listIterator(from);
        }

        @Override
        public List<T> subList(int from, int to) {
            return this.list.subList(from, to);
        }

        @Override
        public String toString() {
            return this.list.toString();
        }
    }

    private static class MutableObservableListWrapper<T> implements ObservableList<T> {

        private final List<T> list;

        private final ListListenersHelper<T> helper = new ListListenersHelper<>();

        MutableObservableListWrapper(List<T> list) {
            this.list = list;
        }

        @Override
        public final void addListener(InvalidationListener listener) {
            this.helper.addListener(listener);
        }

        @Override
        public final void removeListener(InvalidationListener listener) {
            this.helper.removeListener(listener);
        }

        @Override
        public final void addListener(ListChangeListener<T> listener) {
            this.helper.addListener(listener);
        }

        @Override
        public final void removeListener(ListChangeListener<T> listener) {
            this.helper.removeListener(listener);
        }

        protected final void fireChangeEvent(Collection<T> added, Collection<T> removed) {
            this.helper.fireChangeEvent(this, added, removed);
        }

        @Override
        public int size() {
            return this.list.size();
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public boolean contains(Object element) {
            return this.list.contains(element);
        }

        @Override
        public Iterator<T> iterator() {
            return this.list.iterator();
        }

        @Override
        public Object[] toArray() {
            return this.list.toArray();
        }

        @Override
        public <T1> T1[] toArray(T1[] array) {
            return this.list.toArray(array);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.list.containsAll(collection);
        }

        @Override
        public T get(int index) {
            return this.list.get(index);
        }

        @Override
        public int indexOf(Object element) {
            return this.list.indexOf(element);
        }

        @Override
        public int lastIndexOf(Object element) {
            return this.list.lastIndexOf(element);
        }

        @Override
        public ListIterator<T> listIterator() {
            return this.list.listIterator();
        }

        @Override
        public ListIterator<T> listIterator(int from) {
            return this.list.listIterator(from);
        }

        @Override
        public List<T> subList(int from, int to) {
            return this.list.subList(from, to);
        }

        @Override
        public boolean add(T element) {
            this.list.add(element);
            fireChangeEvent(Collections.singletonList(element), Collections.emptyList());
            return true;
        }

        @Override
        public boolean remove(Object element) {
            if (this.list.remove(element)) {
                fireChangeEvent(Collections.emptyList(), Collections.singletonList((T) element));
                return true;
            }
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends T> collection) {
            if (this.list.addAll(collection)) {
                fireChangeEvent(Collections.unmodifiableCollection(collection), Collections.emptyList());
                return true;
            }
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends T> collection) {
            if (this.list.addAll(collection)) {
                fireChangeEvent(Collections.unmodifiableCollection(collection), Collections.emptyList());
                return true;
            }
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            if (this.list.removeAll(collection)) {
                fireChangeEvent(Collections.emptyList(), Collections.unmodifiableCollection((Collection<T>) collection));
                return true;
            }
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            final List<T> removed = new ArrayList<>(this.list);
            removed.removeAll(collection);

            if (this.list.retainAll(collection)) {
                fireChangeEvent(Collections.emptyList(), removed);
                return true;
            }
            return false;
        }

        @Override
        public void clear() {
            if (this.list.size() > 0) {
                final List<T> old = new ArrayList<>(this.list);

                this.list.clear();

                fireChangeEvent(Collections.emptyList(), Collections.unmodifiableList(old));
            }
        }

        @Override
        public T set(int index, T element) {
            final T removed = this.list.set(index, element);

            fireChangeEvent(Collections.singletonList(element), Collections.singletonList(removed));

            return removed;
        }

        @Override
        public void add(int index, T element) {
            this.list.add(index, element);

            fireChangeEvent(Collections.singletonList(element), Collections.emptyList());
        }

        @Override
        public T remove(int index) {
            final T removed = this.list.remove(index);

            if (removed != null) {
                fireChangeEvent(Collections.emptyList(), Collections.singletonList(removed));
            }

            return removed;
        }

        @Override
        public String toString() {
            return this.list.toString();
        }
    }
}

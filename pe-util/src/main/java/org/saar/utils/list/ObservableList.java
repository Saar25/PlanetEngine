package org.saar.utils.list;

import java.util.*;

public class ObservableList<T> implements List<T> {

    private final List<T> list;

    private ListChangeListenersHelper<T> helper = ListChangeListenersHelper.empty();

    public ObservableList(List<T> list) {
        this.list = list;
    }

    public static <T> ObservableList<T> observableArrayList() {
        return new ObservableList<>(new ArrayList<>());
    }

    public static <T> ObservableList<T> observableLinkedList() {
        return new ObservableList<>(new LinkedList<>());
    }

    public void addListener(ListChangeListener<T> listener) {
        this.helper = this.helper.add(listener);
    }

    public void removeListener(ListChangeListener<T> listener) {
        this.helper = this.helper.remove(listener);
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
    public boolean contains(Object o) {
        return this.list.contains(o);
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
    public <E> E[] toArray(E[] a) {
        return this.list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        this.helper.fireEvent(new ListChangeEvent<>(this,
                Collections.singletonList(t),
                Collections.emptyList()));
        return this.list.add(t);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        if (this.list.remove(o)) {
            this.helper.fireEvent(new ListChangeEvent<>(this,
                    Collections.emptyList(),
                    Collections.singletonList((T) o)));
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return this.list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return this.list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.list.retainAll(c);
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public T get(int index) {
        return this.list.get(index);
    }

    @Override
    public T set(int index, T element) {
        return this.list.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        this.list.add(index, element);
        this.helper.fireEvent(new ListChangeEvent<>(this,
                Collections.singletonList(element),
                Collections.emptyList()));
    }

    @Override
    public T remove(int index) {
        final T remove = this.list.remove(index);
        this.helper.fireEvent(new ListChangeEvent<>(this,
                Collections.emptyList(),
                Collections.singletonList(remove)));
        return remove;
    }

    @Override
    public int indexOf(Object o) {
        return this.list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return this.list.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return this.list.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}

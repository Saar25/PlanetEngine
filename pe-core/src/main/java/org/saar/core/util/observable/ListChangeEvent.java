package org.saar.core.util.observable;

import java.util.Collection;

public class ListChangeEvent<T> {

    private final ObservableList<T> list;
    private final Collection<T> added;
    private final Collection<T> removed;

    public ListChangeEvent(ObservableList<T> list, Collection<T> added, Collection<T> removed) {
        this.list = list;
        this.added = added;
        this.removed = removed;
    }

    public ObservableList<T> getList() {
        return this.list;
    }

    public Collection<T> getAdded() {
        return this.added;
    }

    public Collection<T> getRemoved() {
        return this.removed;
    }
}

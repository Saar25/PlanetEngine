package org.saar.core.util.observable;

import org.jproperty.InvalidationListener;
import org.jproperty.InvalidationListenersHelper;

import java.util.Collection;

public class ListListenersHelper<T> {

    private ListChangeListenersHelper<T> listChangeHelper = ListChangeListenersHelper.empty();
    private InvalidationListenersHelper invalidationHelper = InvalidationListenersHelper.empty();

    public void addListener(ListChangeListener<? super T> listener) {
        this.listChangeHelper = this.listChangeHelper.addListener(listener);
    }

    public void removeListener(ListChangeListener<? super T> listener) {
        this.listChangeHelper = this.listChangeHelper.removeListener(listener);
    }

    public void addListener(InvalidationListener listener) {
        this.invalidationHelper = this.invalidationHelper.addListener(listener);
    }

    public void removeListener(InvalidationListener listener) {
        this.invalidationHelper = this.invalidationHelper.removeListener(listener);
    }

    public void fireChangeEvent(ObservableList<T> observable, Collection<T> added, Collection<T> removed) {
        this.listChangeHelper.fireEvent(observable, added, removed);
        this.invalidationHelper.fireEvent(observable);
    }
}

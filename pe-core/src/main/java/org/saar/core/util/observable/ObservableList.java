package org.saar.core.util.observable;

import org.jproperty.Observable;

import java.util.List;

public interface ObservableList<T> extends Observable, List<T> {

    void addListener(ListChangeListener<T> listener);

    void removeListener(ListChangeListener<T> listener);

}

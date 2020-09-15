package org.saar.utils.property;

public interface ReadOnlyProperty<T> {

    void addListener(ChangeListener<? super T> l);

    void removeListener(ChangeListener<? super T> l);

    T getValue();

}

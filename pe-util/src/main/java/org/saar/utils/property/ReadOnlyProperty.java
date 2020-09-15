package org.saar.utils.property;

public interface ReadOnlyProperty<T> {

    void addListener(ChangeListener<T> l);

    void removeListener(ChangeListener<T> l);

    T getValue();

}

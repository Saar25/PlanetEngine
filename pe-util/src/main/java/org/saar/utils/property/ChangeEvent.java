package org.saar.utils.property;

public interface ChangeEvent<T> {

    ReadOnlyProperty<T> getProperty();

    T getOldValue();

    T getNewValue();
}

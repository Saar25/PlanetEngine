package org.saar.utils.property;

public interface ChangeListener<T> {

    void onChange(ChangeEvent<? extends T> e);

}

package org.saar.core.util.observable;

@FunctionalInterface
public interface ListChangeListener<T> {

    void onChange(ListChangeEvent<? extends T> e);

}

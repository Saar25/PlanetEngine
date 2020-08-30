package org.saar.utils.property;

public interface ReadOnlyProperty<T> extends Observable<T> {

    /**
     * Returns the value
     *
     * @return the value
     */
    T getValue();

}

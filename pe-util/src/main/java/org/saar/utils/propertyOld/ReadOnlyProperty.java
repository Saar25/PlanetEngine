package org.saar.utils.propertyOld;

public interface ReadOnlyProperty<T> extends Observable<T> {

    /**
     * Returns the value
     *
     * @return the value
     */
    T getValue();

}

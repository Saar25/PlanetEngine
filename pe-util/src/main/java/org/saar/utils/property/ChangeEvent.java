package org.saar.utils.property;

public class ChangeEvent<T> {

    private final ReadOnlyProperty<T> observable;
    private final T oldValue;
    private final T newValue;

    public ChangeEvent(ReadOnlyProperty<T> observable, T oldValue, T newValue) {
        this.observable = observable;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public ReadOnlyProperty<T> getObservable() {
        return this.observable;
    }

    public T getOldValue() {
        return this.oldValue;
    }

    public T getNewValue() {
        return this.newValue;
    }
}

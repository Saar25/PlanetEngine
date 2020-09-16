package org.saar.utils.property;

public class ChangeEventBase<T> implements ChangeEvent<T> {

    private final ReadOnlyProperty<T> property;
    private final T oldValue;
    private final T newValue;

    public ChangeEventBase(ReadOnlyProperty<T> property, T oldValue, T newValue) {
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public ReadOnlyProperty<T> getProperty() {
        return this.property;
    }

    public T getOldValue() {
        return this.oldValue;
    }

    public T getNewValue() {
        return this.newValue;
    }
}

package org.saar.utils.property.type;

import org.saar.utils.property.ReadOnlyProperty;

public class ReadOnlyIntProperty extends ReadOnlyNumberProperty implements ReadOnlyProperty<Number> {

    protected int value;

    public ReadOnlyIntProperty() {
        this.value = 0;
    }

    public ReadOnlyIntProperty(int value) {
        this.value = value;
    }

    @Override
    public int getIntValue() {
        return get();
    }

    @Override
    public Integer getValue() {
        return get();
    }

    public int get() {
        return this.value;
    }
}

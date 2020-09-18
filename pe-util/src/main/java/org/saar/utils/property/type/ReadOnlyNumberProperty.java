package org.saar.utils.property.type;

import org.saar.utils.property.ChangeListener;
import org.saar.utils.property.ListenersHelper;
import org.saar.utils.property.ReadOnlyProperty;

public abstract class ReadOnlyNumberProperty implements ReadOnlyProperty<Number> {

    protected ListenersHelper<Number> helper = ListenersHelper.empty();

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
        this.helper = this.helper.addListener(listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        this.helper = this.helper.removeListener(listener);
    }

    public int getIntValue() {
        return getValue().intValue();
    }

    public float getFloatValue() {
        return getValue().floatValue();
    }

    public long getLongValue() {
        return getValue().longValue();
    }

    public double getDoubleValue() {
        return getValue().doubleValue();
    }
}

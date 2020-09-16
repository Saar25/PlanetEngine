package org.saar.utils.property.type;

import org.saar.utils.property.ChangeListener;
import org.saar.utils.property.ListenersHelper;
import org.saar.utils.property.ReadOnlyProperty;

public class ReadOnlyIntProperty implements ReadOnlyProperty<Integer> {

    protected ListenersHelper<Integer> helper = ListenersHelper.empty();

    protected int value;

    public ReadOnlyIntProperty() {
        this.value = 0;
    }

    public ReadOnlyIntProperty(int value) {
        this.value = value;
    }

    @Override
    public void addListener(ChangeListener<? super Integer> listener) {
        this.helper = this.helper.addListener(listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Integer> listener) {
        this.helper = this.helper.removeListener(listener);
    }

    @Override
    public Integer getValue() {
        return get();
    }

    public int get() {
        return this.value;
    }
}

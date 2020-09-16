package org.saar.utils.property.type;

import org.saar.utils.property.ChangeListener;
import org.saar.utils.property.ListenersHelper;
import org.saar.utils.property.ReadOnlyProperty;

public class ReadOnlyFloatProperty implements ReadOnlyProperty<Float> {

    protected ListenersHelper<Float> helper = ListenersHelper.empty();

    protected float value;

    public ReadOnlyFloatProperty() {
        this.value = 0f;
    }

    public ReadOnlyFloatProperty(float value) {
        this.value = value;
    }

    @Override
    public void addListener(ChangeListener<? super Float> listener) {
        this.helper = this.helper.addListener(listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Float> listener) {
        this.helper = this.helper.removeListener(listener);
    }

    @Override
    public Float getValue() {
        return get();
    }

    public float get() {
        return this.value;
    }
}

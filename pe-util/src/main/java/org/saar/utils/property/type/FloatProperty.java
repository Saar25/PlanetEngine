package org.saar.utils.property.type;

import org.saar.utils.property.ChangeEventBase;
import org.saar.utils.property.Property;
import org.saar.utils.property.ReadOnlyProperty;
import org.saar.utils.property.binding.Bindings;

public class FloatProperty extends ReadOnlyFloatProperty implements Property<Number> {

    private ReadOnlyProperty<? extends Number> bound = null;

    public FloatProperty() {
    }

    public FloatProperty(float value) {
        super(value);
    }

    @Override
    public void bind(ReadOnlyProperty<? extends Number> observable) {
        Bindings.bind(this, observable);
        this.bound = observable;
    }

    @Override
    public void unbind() {
        if (this.bound != null) {
            Bindings.unbind(this, this.bound);
            this.bound = null;
        }
    }

    @Override
    public void bindBidirectional(Property<Number> observable) {
        Bindings.bindBidirectional(this, observable);
    }

    @Override
    public void unbindBidirectional(Property<Number> observable) {
        Bindings.unbindBidirectional(this, observable);
    }

    @Override
    public void setValue(Number value) {
        set(value.floatValue());
    }

    public void set(float value) {
        if (get() != value) {
            final ChangeEventBase<Number> event = new ChangeEventBase<>(this, this.value, value);

            this.value = value;
            this.helper.fireEvent(event);
        }
    }
}

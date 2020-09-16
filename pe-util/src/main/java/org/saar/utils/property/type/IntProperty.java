package org.saar.utils.property.type;

import org.saar.utils.property.ChangeEventBase;
import org.saar.utils.property.Property;
import org.saar.utils.property.ReadOnlyProperty;
import org.saar.utils.property.binding.Bindings;

public class IntProperty extends ReadOnlyIntProperty implements Property<Integer> {

    private ReadOnlyProperty<? extends Integer> bound = null;

    public IntProperty() {
    }

    public IntProperty(int value) {
        super(value);
    }

    @Override
    public void bind(ReadOnlyProperty<? extends Integer> observable) {
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
    public void bindBidirectional(Property<Integer> observable) {
        Bindings.bindBidirectional(this, observable);
    }

    @Override
    public void unbindBidirectional(Property<Integer> observable) {
        Bindings.unbindBidirectional(this, observable);
    }

    @Override
    public void setValue(Integer value) {
        setValue(value.intValue());
    }

    public void setValue(int value) {
        if (get() != value) {
            final ChangeEventBase<Integer> event = new ChangeEventBase<>(this, this.value, value);

            this.value = value;
            this.helper.fireEvent(event);
        }
    }
}

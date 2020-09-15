package org.saar.utils.property.type;

import org.saar.utils.property.ChangeListener;
import org.saar.utils.property.Property;
import org.saar.utils.property.ReadOnlyProperty;
import org.saar.utils.property.binding.Bindings;

public class ObjectProperty<T> implements Property<T> {

    private ReadOnlyProperty<? extends T> bound = null;

    private T value;

    public ObjectProperty() {
        this.value = null;
    }

    public ObjectProperty(T value) {
        this.value = value;
    }

    @Override
    public void bind(ReadOnlyProperty<? extends T> observable) {
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
    public void bindBidirectional(Property<T> observable) {
        Bindings.bindBidirectional(this, observable);
    }

    @Override
    public void unbindBidirectional(Property<T> observable) {
        Bindings.unbindBidirectional(this, observable);
    }

    @Override
    public void addListener(ChangeListener<? super T> l) {
        // TODO: add listeners
    }

    @Override
    public void removeListener(ChangeListener<? super T> l) {
        // TODO: add listeners
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
        // TODO: invoke listeners
    }
}

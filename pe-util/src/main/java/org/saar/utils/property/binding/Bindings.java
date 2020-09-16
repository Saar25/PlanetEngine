package org.saar.utils.property.binding;

import org.saar.utils.property.Property;
import org.saar.utils.property.ReadOnlyProperty;

public final class Bindings {

    private Bindings() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static <T> void bind(Property<T> property1, ReadOnlyProperty<? extends T> property2) {
        property1.setValue(property2.getValue());
        property2.addListener(UnidirectionalBinding.bind(property1));
    }

    public static <T> void unbind(Property<T> property1, ReadOnlyProperty<? extends T> property2) {
        property2.removeListener(UnidirectionalBinding.bind(property1));
    }

    public static <T> void bindBidirectional(Property<T> property1, Property<T> property2) {
        property1.setValue(property2.getValue());
        final BidirectionalBinding<T> bind = BidirectionalBinding.bind(property1, property2);
        property1.addListener(bind);
        property2.addListener(bind);
    }

    public static <T> void unbindBidirectional(Property<T> property1, Property<T> property2) {
        final BidirectionalBinding<T> bind = BidirectionalBinding.bind(property1, property2);
        property1.removeListener(bind);
        property2.removeListener(bind);
    }
}

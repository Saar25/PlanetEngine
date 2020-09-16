package org.saar.utils.property.binding;

import org.saar.utils.property.ChangeEvent;
import org.saar.utils.property.ChangeListener;
import org.saar.utils.property.Property;

import java.util.Objects;

public abstract class BidirectionalBinding<T> implements ChangeListener<T> {

    public abstract Object getProperty1();

    public abstract Object getProperty2();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BidirectionalBinding<?> that = (BidirectionalBinding<?>) o;
        return Objects.equals(getProperty1(), that.getProperty1()) &&
                Objects.equals(getProperty2(), that.getProperty2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProperty1(), getProperty2());
    }

    public static <T> BidirectionalBinding<T> bind(Property<T> property1, Property<T> property2) {
        return new BidirectionalBinding.GenericBidirectionalBinding<>(property1, property2);
    }

    private static class GenericBidirectionalBinding<T> extends BidirectionalBinding<T> {

        private final Property<T> property1;
        private final Property<T> property2;
        private boolean updating = false;

        public GenericBidirectionalBinding(Property<T> property1, Property<T> property2) {
            this.property1 = property1;
            this.property2 = property2;
        }

        @Override
        public Property<T> getProperty1() {
            return this.property1;
        }

        @Override
        public Property<T> getProperty2() {
            return this.property2;
        }

        @Override
        public void onChange(ChangeEvent<? extends T> e) {
            if (!this.updating) {
                this.updating = true;

                if (getProperty1() == e.getProperty()) {
                    getProperty2().setValue(e.getNewValue());
                } else {
                    getProperty1().setValue(e.getNewValue());
                }

                this.updating = false;
            }
        }
    }

}

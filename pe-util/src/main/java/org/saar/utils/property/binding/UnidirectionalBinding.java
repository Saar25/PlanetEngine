package org.saar.utils.property.binding;

import org.saar.utils.property.ChangeEvent;
import org.saar.utils.property.ChangeListener;
import org.saar.utils.property.Property;

import java.util.Objects;

public abstract class UnidirectionalBinding<T> implements ChangeListener<T> {

    public abstract Object getProperty();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnidirectionalBinding<?> that = (UnidirectionalBinding<?>) o;
        return Objects.equals(getProperty(), that.getProperty());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProperty());
    }

    public static <T> UnidirectionalBinding<T> bind(Property<T> property) {
        return new GenericUnidirectionalBinding<>(property);
    }

    private static class GenericUnidirectionalBinding<T> extends UnidirectionalBinding<T> {

        private final Property<T> property;

        public GenericUnidirectionalBinding(Property<T> property) {
            this.property = property;
        }

        @Override
        public Property<T> getProperty() {
            return this.property;
        }

        @Override
        public void onChange(ChangeEvent<T> e) {
            getProperty().setValue(e.getNewValue());
        }
    }

}

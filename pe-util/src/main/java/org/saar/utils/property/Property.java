package org.saar.utils.property;

public interface Property<T> extends ReadOnlyProperty<T> {

    void bind(ReadOnlyProperty<? extends T> observable);

    void unbind();

    void bindBidirectional(Property<? extends T> observable);

    void unbindBidirectional(Property<? extends T> observable);

    void setValue(T value);
}

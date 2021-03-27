package org.saar.lwjgl.opengl.shaders.uniforms;

import org.jproperty.type.BooleanProperty;
import org.jproperty.type.SimpleBooleanProperty;

public class BooleanUniformValue extends BooleanUniform implements UniformValue<Boolean> {

    private final BooleanProperty property = new SimpleBooleanProperty();

    private final String name;

    public BooleanUniformValue(String name) {
        this.name = name;
    }

    @Override
    public final boolean getUniformValue() {
        return getValue();
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final Boolean getValue() {
        return get();
    }

    @Override
    public final void setValue(Boolean value) {
        set(value);
    }

    public final BooleanProperty valueProperty() {
        return this.property;
    }

    public final boolean get() {
        return valueProperty().get();
    }

    public final void set(boolean value) {
        valueProperty().set(value);
    }
}

package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.jproperty.type.FloatProperty;
import org.jproperty.type.SimpleFloatProperty;

public class FloatUniformProperty extends FloatUniform implements UniformValue<Float> {

    private final FloatProperty property = new SimpleFloatProperty(0f);

    private final String name;

    public FloatUniformProperty(String name) {
        this.name = name;
    }

    public final FloatProperty valueProperty() {
        return this.property;
    }

    @Override
    public final float getUniformValue() {
        return get();
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final Float getValue() {
        return valueProperty().get();
    }

    @Override
    public final void setValue(Float value) {
        valueProperty().set(value);
    }

    public final float get() {
        return valueProperty().get();
    }

    public final void set(float value) {
        valueProperty().set(value);
    }
}

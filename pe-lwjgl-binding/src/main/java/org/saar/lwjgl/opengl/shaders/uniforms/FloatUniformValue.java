package org.saar.lwjgl.opengl.shaders.uniforms;

import org.jproperty.type.FloatProperty;
import org.jproperty.type.SimpleFloatProperty;

public class FloatUniformValue extends FloatUniform implements UniformValue<Float> {

    private final FloatProperty property = new SimpleFloatProperty(0f);

    private final String name;

    public FloatUniformValue(String name) {
        this.name = name;
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
        return get();
    }

    @Override
    public final void setValue(Float value) {
        set(value);
    }

    public final FloatProperty valueProperty() {
        return this.property;
    }

    public final float get() {
        return valueProperty().get();
    }

    public final void set(float value) {
        valueProperty().set(value);
    }
}

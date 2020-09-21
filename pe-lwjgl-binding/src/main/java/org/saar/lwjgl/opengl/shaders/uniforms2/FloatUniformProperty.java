package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.jproperty.type.FloatProperty;

public class FloatUniformProperty extends FloatUniform implements UniformProperty<Float> {

    private final FloatProperty property = new FloatProperty(0f);

    private final String name;

    public FloatUniformProperty(String name) {
        this.name = name;
    }

    @Override
    public float getUniformValue() {
        return valueProperty().get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public FloatProperty valueProperty() {
        return this.property;
    }
}

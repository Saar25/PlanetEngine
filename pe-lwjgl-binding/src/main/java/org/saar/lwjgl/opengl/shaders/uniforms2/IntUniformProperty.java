package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.jproperty.type.IntProperty;

public class IntUniformProperty extends IntUniform implements UniformProperty<Integer> {

    private final IntProperty property = new IntProperty(0);

    private final String name;

    public IntUniformProperty(String name) {
        this.name = name;
    }

    @Override
    public int getUniformValue() {
        return valueProperty().get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IntProperty valueProperty() {
        return this.property;
    }
}

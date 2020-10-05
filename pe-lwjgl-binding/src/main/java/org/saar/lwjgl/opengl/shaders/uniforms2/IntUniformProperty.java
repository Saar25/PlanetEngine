package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.jproperty.type.IntProperty;
import org.jproperty.type.SimpleIntProperty;

public class IntUniformProperty extends IntUniform implements UniformProperty<Integer> {

    private final IntProperty property = new SimpleIntProperty(0);

    private final String name;

    public IntUniformProperty(String name) {
        this.name = name;
    }

    @Override
    public final int getUniformValue() {
        return get();
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final IntProperty valueProperty() {
        return this.property;
    }

    @Override
    public final Integer getValue() {
        return valueProperty().get();
    }

    @Override
    public final void setValue(Integer value) {
        valueProperty().set(value);
    }

    public final int get() {
        return valueProperty().get();
    }

    public final void set(int value) {
        valueProperty().set(value);
    }
}

package org.saar.lwjgl.opengl.shaders.uniforms;

import org.jproperty.type.IntProperty;
import org.jproperty.type.SimpleIntProperty;

public class UIntUniformValue extends UIntUniform implements UniformValue<Integer> {

    private final IntProperty property = new SimpleIntProperty(0);

    private final String name;

    public UIntUniformValue(String name) {
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
    public final Integer getValue() {
        return get();
    }

    @Override
    public final void setValue(Integer value) {
        set(value);
    }

    public final IntProperty valueProperty() {
        return this.property;
    }

    public final int get() {
        return valueProperty().get();
    }

    public final void set(int value) {
        valueProperty().set(value);
    }
}

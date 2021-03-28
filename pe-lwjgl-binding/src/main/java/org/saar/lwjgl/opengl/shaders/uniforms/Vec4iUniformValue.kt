package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector4i;
import org.joml.Vector4ic;

public class Vec4iUniformValue extends Vec4iUniform implements UniformValue<Vector4ic> {

    private final String name;

    private final Vector4i value = new Vector4i();

    public Vec4iUniformValue(String name) {
        this.name = name;
    }

    @Override
    public final Vector4ic getUniformValue() {
        return getValue();
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final Vector4i getValue() {
        return this.value;
    }

    @Override
    public final void setValue(Vector4ic value) {
        getValue().set(value);
    }
}

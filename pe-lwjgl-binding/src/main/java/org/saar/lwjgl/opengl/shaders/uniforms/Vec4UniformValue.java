package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.saar.maths.utils.Vector4;

public class Vec4UniformValue extends Vec4Uniform implements UniformValue<Vector4fc> {

    private final String name;

    private final Vector4f value = Vector4.create();

    public Vec4UniformValue(String name) {
        this.name = name;
    }

    @Override
    public final Vector4fc getUniformValue() {
        return getValue();
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final Vector4f getValue() {
        return this.value;
    }

    @Override
    public final void setValue(Vector4fc value) {
        getValue().set(value);
    }
}

package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.maths.utils.Matrix4;

public class Mat4UniformValue extends Mat4Uniform implements UniformValue<Matrix4fc> {

    private final String name;

    private final Matrix4f value = Matrix4.create();

    public Mat4UniformValue(String name) {
        this.name = name;
    }

    @Override
    public final Matrix4fc getUniformValue() {
        return getValue();
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final Matrix4f getValue() {
        return this.value;
    }

    @Override
    public final void setValue(Matrix4fc value) {
        getValue().set(value);
    }
}

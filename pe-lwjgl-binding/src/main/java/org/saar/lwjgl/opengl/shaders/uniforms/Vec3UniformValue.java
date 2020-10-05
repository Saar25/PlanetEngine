package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.maths.utils.Vector3;

public class Vec3UniformValue extends Vec3Uniform implements UniformValue<Vector3fc> {

    private final String name;

    private final Vector3f value = Vector3.create();

    public Vec3UniformValue(String name) {
        this.name = name;
    }

    @Override
    public final Vector3fc getUniformValue() {
        return getValue();
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final Vector3f getValue() {
        return this.value;
    }

    @Override
    public final void setValue(Vector3fc value) {
        getValue().set(value);
    }
}

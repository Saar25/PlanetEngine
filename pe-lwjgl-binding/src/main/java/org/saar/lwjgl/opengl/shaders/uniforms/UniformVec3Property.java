package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector3fc;
import org.lwjgl.opengl.GL20;

public abstract class UniformVec3Property<T> extends UniformPropertyBase<T, Vector3fc> {

    public UniformVec3Property(String name) {
        super(name);
    }

    @Override
    public void load(Vector3fc value) {
        GL20.glUniform3f(getLocation(), value.x(), value.y(), value.z());
    }
}

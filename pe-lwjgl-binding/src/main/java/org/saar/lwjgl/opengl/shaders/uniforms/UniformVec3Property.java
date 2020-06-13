package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector3fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.RenderState;

public abstract class UniformVec3Property<T> extends AbstractUniformProperty<T> {

    public UniformVec3Property(String name) {
        super(name);
    }

    @Override
    public void load(RenderState<T> state) {
        if (valueAvailable()) {
            final Vector3fc value = getUniformValue(state);
            GL20.glUniform3f(getLocation(), value.x(), value.y(), value.z());
        }
    }

    public abstract Vector3fc getUniformValue(RenderState<T> state);
}

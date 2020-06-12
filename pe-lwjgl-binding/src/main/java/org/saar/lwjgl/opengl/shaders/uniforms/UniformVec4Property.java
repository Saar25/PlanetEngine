package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector4fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.RenderState;

public abstract class UniformVec4Property<T> extends AbstractUniformProperty<T> {

    public UniformVec4Property(String name) {
        super(name);
    }

    @Override
    public void load(RenderState<T> state) {
        if (valueAvailable()) {
            final Vector4fc value = getUniformValue(state);
            GL20.glUniform4f(getLocation(), value.x(), value.y(), value.z(), value.w());
        }
    }

    public abstract Vector4fc getUniformValue(RenderState<T> state);
}

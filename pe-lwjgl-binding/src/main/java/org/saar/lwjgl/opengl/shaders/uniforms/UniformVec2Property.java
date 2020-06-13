package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector2fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.RenderState;

public abstract class UniformVec2Property<T> extends AbstractUniformProperty<T> {

    public UniformVec2Property(String name) {
        super(name);
    }

    @Override
    public void load(RenderState<T> state) {
        if (valueAvailable()) {
            final Vector2fc value = getUniformValue(state);
            GL20.glUniform2f(getLocation(), value.x(), value.y());
        }
    }

    public abstract Vector2fc getUniformValue(RenderState<T> state);
}

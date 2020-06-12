package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Matrix4fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.RenderState;

public abstract class UniformMat4Property<T> extends AbstractUniformProperty<T> {

    public UniformMat4Property(String name) {
        super(name);
    }

    @Override
    public void load(RenderState<T> state) {
        if (valueAvailable()) {
            final Matrix4fc value = getUniformValue(state);
            final float[] buffer = value.get(new float[16]);
            GL20.glUniformMatrix4fv(getLocation(), false, buffer);
        }
    }

    public abstract Matrix4fc getUniformValue(RenderState<T> state);
}

package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Matrix4fc;
import org.lwjgl.opengl.GL20;

public abstract class UniformMat4Property<T> extends UniformPropertyBase<T, Matrix4fc> {

    public UniformMat4Property(String name) {
        super(name);
    }

    @Override
    public void load(Matrix4fc value) {
        final float[] buffer = value.get(new float[16]);
        GL20.glUniformMatrix4fv(getLocation(), false, buffer);
    }
}

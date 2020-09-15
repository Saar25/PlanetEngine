package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector2fc;
import org.lwjgl.opengl.GL20;

public abstract class UniformVec2Property<T> extends UniformPropertyBase<T, Vector2fc> {

    public UniformVec2Property(String name) {
        super(name);
    }

    @Override
    public void load(Vector2fc value) {
        GL20.glUniform2f(getLocation(), value.x(), value.y());
    }

}

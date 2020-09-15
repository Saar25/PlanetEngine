package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector4fc;
import org.lwjgl.opengl.GL20;

public abstract class UniformVec4Property<T> extends UniformPropertyBase<T, Vector4fc> {

    public UniformVec4Property(String name) {
        super(name);
    }

    public void load(Vector4fc value) {
        GL20.glUniform4f(getLocation(), value.x(),
                value.y(), value.z(), value.w());
    }
}

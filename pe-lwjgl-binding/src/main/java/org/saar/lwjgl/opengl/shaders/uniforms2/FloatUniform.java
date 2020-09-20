package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.lwjgl.opengl.GL20;

public abstract class FloatUniform extends UniformBase implements Uniform {

    @Override
    public void doLoad() {
        GL20.glUniform1f(getLocation(), getUniformValue());
    }

    public abstract float getUniformValue();
}

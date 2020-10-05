package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.lwjgl.opengl.GL20;

public abstract class IntUniform extends UniformBase implements Uniform {

    @Override
    public final void doLoad() {
        GL20.glUniform1i(getLocation(), getUniformValue());
    }

    public abstract int getUniformValue();
}

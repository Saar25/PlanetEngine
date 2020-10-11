package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL20;

public abstract class BooleanUniform extends UniformBase implements Uniform {

    @Override
    public final void doLoad() {
        final int v = getUniformValue() ? 1 : 0;
        GL20.glUniform1i(getLocation(), v);
    }

    public abstract boolean getUniformValue();
}

package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL30;

public abstract class UIntUniform extends UniformBase implements Uniform {

    @Override
    public final void doLoad() {
        GL30.glUniform1ui(getLocation(), getUniformValue());
    }

    public abstract int getUniformValue();
}

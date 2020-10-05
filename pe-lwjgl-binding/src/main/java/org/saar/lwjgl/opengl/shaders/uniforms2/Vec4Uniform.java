package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.joml.Vector4fc;
import org.lwjgl.opengl.GL20;

public abstract class Vec4Uniform extends UniformBase implements Uniform {

    @Override
    public final void doLoad() {
        final Vector4fc v = getUniformValue();
        GL20.glUniform4f(getLocation(), v.x(), v.y(), v.z(), v.w());
    }

    public abstract Vector4fc getUniformValue();
}

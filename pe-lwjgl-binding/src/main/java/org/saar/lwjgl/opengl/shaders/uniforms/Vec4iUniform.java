package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector4ic;
import org.lwjgl.opengl.GL20;

public abstract class Vec4iUniform extends UniformBase implements Uniform {

    @Override
    public final void doLoad() {
        final Vector4ic v = getUniformValue();
        GL20.glUniform4i(getLocation(), v.x(), v.y(), v.z(), v.w());
    }

    public abstract Vector4ic getUniformValue();
}

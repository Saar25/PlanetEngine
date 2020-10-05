package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.joml.Vector3fc;
import org.lwjgl.opengl.GL20;

public abstract class Vec3Uniform extends UniformBase implements Uniform {

    @Override
    public final void doLoad() {
        final Vector3fc v = getUniformValue();
        GL20.glUniform3f(getLocation(), v.x(), v.y(), v.z());
    }

    public abstract Vector3fc getUniformValue();
}

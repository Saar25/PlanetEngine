package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector2fc;
import org.lwjgl.opengl.GL20;

public abstract class Vec2Uniform extends UniformBase implements Uniform {

    @Override
    public final void doLoad() {
        final Vector2fc v = getUniformValue();
        GL20.glUniform2f(getLocation(), v.x(), v.y());
    }

    public abstract Vector2fc getUniformValue();
}

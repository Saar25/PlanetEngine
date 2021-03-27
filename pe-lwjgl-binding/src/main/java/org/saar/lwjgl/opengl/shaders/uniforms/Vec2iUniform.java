package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector2ic;
import org.lwjgl.opengl.GL20;

public abstract class Vec2iUniform extends UniformBase implements Uniform {

    @Override
    public final void doLoad() {
        final Vector2ic v = getUniformValue();
        GL20.glUniform2i(getLocation(), v.x(), v.y());
    }

    public abstract Vector2ic getUniformValue();
}

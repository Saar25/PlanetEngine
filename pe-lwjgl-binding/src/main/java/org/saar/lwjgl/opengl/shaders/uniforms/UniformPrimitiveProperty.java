package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.primitive.GlPrimitive;
import org.saar.lwjgl.opengl.shaders.RenderState;

public abstract class UniformPrimitiveProperty<T> extends AbstractUniformProperty<T> {

    public UniformPrimitiveProperty(String name) {
        super(name);
    }

    @Override
    public void load(RenderState<T> state) {
        if (valueAvailable()) {
            final GlPrimitive value = getUniformValue(state);
            value.loadUniform(getLocation());
        }
    }

    public abstract GlPrimitive getUniformValue(RenderState<T> state);
}

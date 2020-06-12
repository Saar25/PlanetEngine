package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.shaders.RenderState;

public abstract class UniformValueProperty<T> extends AbstractUniformProperty<T> {

    protected UniformValueProperty(String name) {
        super(name);
    }

    @Override
    public void load(RenderState<T> state) {
        if (valueAvailable()) {
            final UniformValue value = getUniformValue(state);
            value.load(getLocation());
        }
    }

    public abstract UniformValue getUniformValue(RenderState<T> state);

}

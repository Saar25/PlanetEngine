package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.primitive.GlPrimitive;

public abstract class UniformGlPrimitiveProperty<T> extends UniformPropertyBase<T, GlPrimitive> {

    public UniformGlPrimitiveProperty(String name) {
        super(name);
    }

    @Override
    public void load(GlPrimitive value) {
        value.loadUniform(getLocation());
    }
}

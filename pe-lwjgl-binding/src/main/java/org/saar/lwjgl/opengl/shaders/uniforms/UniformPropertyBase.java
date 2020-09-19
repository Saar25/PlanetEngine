package org.saar.lwjgl.opengl.shaders.uniforms;

public abstract class UniformPropertyBase<T> extends AbstractUniformProperty<T> implements UniformProperty<T> {

    UniformPropertyBase(String name) {
        super(name);
    }

    public abstract void loadValue(T value);
}

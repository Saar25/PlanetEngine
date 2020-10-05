package org.saar.lwjgl.opengl.shaders.uniforms2;

public interface WritableUniform<T> extends Uniform {

    void setValue(T value);

}

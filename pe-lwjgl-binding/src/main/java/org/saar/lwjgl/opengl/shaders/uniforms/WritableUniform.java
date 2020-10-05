package org.saar.lwjgl.opengl.shaders.uniforms;

public interface WritableUniform<T> extends Uniform {

    void setValue(T value);

}

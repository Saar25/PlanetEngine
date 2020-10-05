package org.saar.lwjgl.opengl.shaders.uniforms2;

public interface UniformValue<T> extends WritableUniform<T> {

    T getValue();

}

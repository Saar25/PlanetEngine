package org.saar.lwjgl.opengl.shaders.uniforms;

public interface UniformValue<T> extends WritableUniform<T> {

    T getValue();

}

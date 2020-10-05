package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public interface Uniform {

    void initialize(ShadersProgram shadersProgram);

    void load();
}

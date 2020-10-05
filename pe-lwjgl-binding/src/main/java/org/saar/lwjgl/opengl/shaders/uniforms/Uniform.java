package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public interface Uniform {

    void initialize(ShadersProgram shadersProgram);

    void load();
}

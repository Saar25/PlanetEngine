package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.shaders.RenderState;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public interface UniformProperty<T> {

    void load(RenderState<T> state);

    void initialize(ShadersProgram<T> shadersProgram);

    default boolean valueAvailable() {
        return true;
    }
}

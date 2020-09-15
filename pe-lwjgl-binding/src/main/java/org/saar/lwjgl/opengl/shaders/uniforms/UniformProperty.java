package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public interface UniformProperty<T> {

    void initialize(ShadersProgram shadersProgram);

    void loadOnInstance(InstanceRenderState<T> state);

    void loadOnStage(StageRenderState state);
}

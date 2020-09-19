package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public abstract class StageUniform<T> implements UniformProperty.Stage<T> {

    private final UniformProperty<T> uniform;

    public StageUniform(UniformProperty<T> uniform) {
        this.uniform = uniform;
    }

    @Override
    public void initialize(ShadersProgram shadersProgram) {
        this.uniform.initialize(shadersProgram);
    }

    @Override
    public void loadValue(T value) {
        this.uniform.loadValue(value);
    }

    @Override
    public void loadOnStage(StageRenderState state) {
        loadValue(getUniformValue(state));
    }

    public abstract T getUniformValue(StageRenderState state);
}

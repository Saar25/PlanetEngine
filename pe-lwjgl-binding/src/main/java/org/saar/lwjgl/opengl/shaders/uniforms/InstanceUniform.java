package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public abstract class InstanceUniform<T, E> implements UniformProperty.Instance<T, E> {

    private final UniformProperty<E> uniform;

    public InstanceUniform(UniformProperty<E> uniform) {
        this.uniform = uniform;
    }

    @Override
    public void initialize(ShadersProgram shadersProgram) {
        this.uniform.initialize(shadersProgram);
    }

    @Override
    public void loadValue(E value) {
        this.uniform.loadValue(value);
    }

    @Override
    public void loadOnInstance(InstanceRenderState<T> state) {
        loadValue(getUniformValue(state));
    }

    public abstract E getUniformValue(InstanceRenderState<T> state);
}

package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public interface UniformProperty<T> {

    void initialize(ShadersProgram shadersProgram);

    void loadValue(T value);

    interface Stage<T> extends UniformProperty<T> {

        void loadOnStage(StageRenderState state);

    }

    interface Instance<T, E> extends UniformProperty<E> {

        void loadOnInstance(InstanceRenderState<T> state);

    }
}

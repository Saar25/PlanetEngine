package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public abstract class UniformPropertyBase<T, E> extends AbstractUniformProperty<T> implements UniformProperty<T> {

    UniformPropertyBase(String name) {
        super(name);
    }

    @Override
    public final void loadOnInstance(InstanceRenderState<T> state) {
        loadCheckNull(getInstanceValue(state));
    }

    @Override
    public final void loadOnStage(StageRenderState state) {
        loadCheckNull(getStageValue(state));
    }

    private void loadCheckNull(E value) {
        if (value != null) load(value);
    }

    public abstract void load(E value);

    public E getInstanceValue(InstanceRenderState<T> state) {
        return null;
    }

    public E getStageValue(StageRenderState state) {
        return null;
    }
}

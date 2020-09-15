package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public abstract class UniformBooleanProperty<T> extends AbstractUniformProperty<T> {

    protected UniformBooleanProperty(String name) {
        super(name);
    }

    public void load(boolean value) {
        GL20.glUniform1i(getLocation(), value ? 1 : 0);
    }

    @Override
    public void loadOnInstance(InstanceRenderState<T> state) {
        load(getInstanceValue(state));
    }

    @Override
    public void loadOnStage(StageRenderState state) {
        load(getStageValue(state));
    }

    public boolean getInstanceValue(InstanceRenderState<T> state) {
        return false;
    }

    public boolean getStageValue(StageRenderState state) {
        return false;
    }
}

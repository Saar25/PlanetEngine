package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public abstract class UniformBooleanProperty<T> extends AbstractUniformProperty<T> {

    private boolean loadOnInstance = true;
    private boolean loadOnStage = true;

    protected UniformBooleanProperty(String name) {
        super(name);
    }

    public void load(boolean value) {
        GL20.glUniform1i(getLocation(), value ? 1 : 0);
    }

    @Override
    public void loadOnInstance(InstanceRenderState<T> state) {
        final boolean value = getInstanceValue(state);
        if (this.loadOnInstance) load(value);
    }

    @Override
    public void loadOnStage(StageRenderState state) {
        final boolean value = getStageValue(state);
        if (this.loadOnStage) load(value);
    }

    public boolean getInstanceValue(InstanceRenderState<T> state) {
        this.loadOnInstance = false;
        return false;
    }

    public boolean getStageValue(StageRenderState state) {
        this.loadOnStage = false;
        return false;
    }
}

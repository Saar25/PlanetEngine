package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public abstract class UniformFloatProperty<T> extends AbstractUniformProperty<T> {

    private boolean loadOnInstance = true;
    private boolean loadOnStage = true;

    protected UniformFloatProperty(String name) {
        super(name);
    }

    public void load(float value) {
        GL30.glUniform1f(getLocation(), value);
    }

    @Override
    public void loadOnInstance(InstanceRenderState<T> state) {
        final float value = getInstanceValue(state);
        if (this.loadOnInstance) load(value);
    }

    @Override
    public void loadOnStage(StageRenderState state) {
        final float value = getStageValue(state);
        if (this.loadOnStage) load(value);
    }

    public float getInstanceValue(InstanceRenderState<T> state) {
        this.loadOnInstance = false;
        return 0;
    }

    public float getStageValue(StageRenderState state) {
        this.loadOnStage = false;
        return 0;
    }
}

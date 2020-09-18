package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public abstract class UniformUIntProperty<T> extends AbstractUniformProperty<T> {

    private boolean loadOnInstance = true;
    private boolean loadOnStage = true;

    protected UniformUIntProperty(String name) {
        super(name);
    }

    public void load(int value) {
        GL30.glUniform1ui(getLocation(), value);
    }

    @Override
    public void loadOnInstance(InstanceRenderState<T> state) {
        final int value = getInstanceValue(state);
        if (this.loadOnInstance) load(value);
    }

    @Override
    public void loadOnStage(StageRenderState state) {
        final int value = getStageValue(state);
        if (this.loadOnStage) load(value);
    }

    public int getInstanceValue(InstanceRenderState<T> state) {
        this.loadOnInstance = false;
        return 0;
    }

    public int getStageValue(StageRenderState state) {
        this.loadOnStage = false;
        return 0;
    }

}

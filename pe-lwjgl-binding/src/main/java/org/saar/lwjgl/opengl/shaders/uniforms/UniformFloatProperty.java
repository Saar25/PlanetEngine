package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public abstract class UniformFloatProperty<T> extends AbstractUniformProperty<T> {

    protected UniformFloatProperty(String name) {
        super(name);
    }

    public void load(float value) {
        GL30.glUniform1f(getLocation(), value);
    }

    @Override
    public void loadOnInstance(InstanceRenderState<T> state) {
        load(getInstanceValue(state));
    }

    @Override
    public void loadOnStage(StageRenderState state) {
        load(getStageValue(state));
    }

    public float getInstanceValue(InstanceRenderState<T> state) {
        return 0f;
    }

    public float getStageValue(StageRenderState state) {
        return 0f;
    }
}

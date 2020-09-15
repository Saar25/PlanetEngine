package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public abstract class UniformIntProperty<T> extends AbstractUniformProperty<T> {

    protected UniformIntProperty(String name) {
        super(name);
    }

    public void load(int value) {
        GL30.glUniform1i(getLocation(), value);
    }

    @Override
    public void loadOnInstance(InstanceRenderState<T> state) {
        load(getInstanceValue(state));
    }

    @Override
    public void loadOnStage(StageRenderState state) {
        load(getStageValue(state));
    }

    public int getInstanceValue(InstanceRenderState<T> state) {
        return 0;
    }

    public int getStageValue(StageRenderState state) {
        return 0;
    }

}

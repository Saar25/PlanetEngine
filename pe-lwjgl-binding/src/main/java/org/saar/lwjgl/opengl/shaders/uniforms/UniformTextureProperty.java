package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.StageRenderState;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public abstract class UniformTextureProperty<T> implements UniformProperty<T> {

    private final String name;
    private final int unit;

    public UniformTextureProperty(String name, int unit) {
        this.name = name;
        this.unit = unit;
    }

    public void load(ReadOnlyTexture value) {
        if (value != null) {
            value.bind(unit);
        }
    }

    @Override
    public void loadOnInstance(InstanceRenderState<T> state) {
        load(getInstanceValue(state));
    }

    @Override
    public void loadOnStage(StageRenderState state) {
        load(getStageValue(state));
    }

    @Override
    public void initialize(ShadersProgram shadersProgram) {
        GL20.glUniform1i(shadersProgram.getUniformLocation(name), unit);
    }

    public ReadOnlyTexture getInstanceValue(InstanceRenderState<T> state) {
        return null;
    }

    public ReadOnlyTexture getStageValue(StageRenderState state) {
        return null;
    }
}

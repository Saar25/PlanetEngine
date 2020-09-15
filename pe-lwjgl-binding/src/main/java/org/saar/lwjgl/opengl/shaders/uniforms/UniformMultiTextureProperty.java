package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.StageRenderState;
import org.saar.lwjgl.opengl.textures.MultiTexture;

public abstract class UniformMultiTextureProperty<T> implements UniformProperty<T> {

    private final String blendMap;
    private final String dTexture;
    private final String rTexture;
    private final String gTexture;
    private final String bTexture;
    private final int unit;

    public UniformMultiTextureProperty(String blendMap, String dTexture, String rTexture,
                                       String gTexture, String bTexture, int unit) {
        this.blendMap = blendMap;
        this.dTexture = dTexture;
        this.rTexture = rTexture;
        this.gTexture = gTexture;
        this.bTexture = bTexture;
        this.unit = unit;
    }

    public void load(MultiTexture value) {
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
        final String[] textures = {blendMap, dTexture, rTexture, gTexture, bTexture};
        for (int i = 0; i < textures.length; i++) {
            GL20.glUniform1i(shadersProgram.getUniformLocation(textures[i]), unit + i);
        }
    }

    public MultiTexture getInstanceValue(InstanceRenderState<T> state) {
        return null;
    }

    public MultiTexture getStageValue(StageRenderState state) {
        return null;
    }
}

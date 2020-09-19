package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.StageRenderState;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public class UniformTextureProperty implements UniformProperty<ReadOnlyTexture> {

    private final String name;
    private final int unit;

    public UniformTextureProperty(String name, int unit) {
        this.name = name;
        this.unit = unit;
    }

    @Override
    public void loadValue(ReadOnlyTexture value) {
        value.bind(this.unit);
    }

    @Override
    public void initialize(ShadersProgram shadersProgram) {
        GL20.glUniform1i(shadersProgram.getUniformLocation(this.name), this.unit);
    }


    public static abstract class Stage extends UniformTextureProperty implements UniformProperty.Stage<ReadOnlyTexture> {
        public Stage(String name, int unit) {
            super(name, unit);
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            loadValue(getUniformValue(state));
        }

        public abstract ReadOnlyTexture getUniformValue(StageRenderState state);
    }

    public static abstract class Instance<T> extends UniformTextureProperty implements UniformProperty.Instance<T, ReadOnlyTexture> {
        public Instance(String name, int unit) {
            super(name, unit);
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            loadValue(getUniformValue(state));
        }

        public abstract ReadOnlyTexture getUniformValue(InstanceRenderState<T> state);
    }
}

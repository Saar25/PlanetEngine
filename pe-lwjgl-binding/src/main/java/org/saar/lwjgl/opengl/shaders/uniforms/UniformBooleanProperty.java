package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public class UniformBooleanProperty extends AbstractUniformProperty<Boolean> {

    public UniformBooleanProperty(String name) {
        super(name);
    }

    @Override
    public void loadValue(Boolean value) {
        load(value);
    }

    public void load(boolean value) {
        GL20.glUniform1i(getLocation(), value ? 1 : 0);
    }

    public static abstract class Stage extends UniformBooleanProperty implements UniformProperty.Stage<Boolean> {
        public Stage(String name) {
            super(name);
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            load(getUniformValue(state));
        }

        public abstract boolean getUniformValue(StageRenderState state);
    }

    public static abstract class Instance<T> extends UniformBooleanProperty implements UniformProperty.Instance<T, Boolean> {
        public Instance(String name) {
            super(name);
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            load(getUniformValue(state));
        }

        public abstract boolean getUniformValue(InstanceRenderState<T> state);
    }
}

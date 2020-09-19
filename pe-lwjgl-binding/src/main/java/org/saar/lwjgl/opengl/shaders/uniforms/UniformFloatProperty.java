package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public class UniformFloatProperty extends AbstractUniformProperty<Float> {

    protected UniformFloatProperty(String name) {
        super(name);
    }

    @Override
    public void loadValue(Float value) {
        load(value);
    }

    public void load(float value) {
        GL20.glUniform1f(getLocation(), value);
    }

    public static abstract class Stage extends UniformFloatProperty implements UniformProperty.Stage<Float> {
        public Stage(String name) {
            super(name);
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            load(getUniformValue(state));
        }

        public abstract float getUniformValue(StageRenderState state);
    }

    public static abstract class Instance<T> extends UniformFloatProperty implements UniformProperty.Instance<T, Float> {
        public Instance(String name) {
            super(name);
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            load(getUniformValue(state));
        }

        public abstract float getUniformValue(InstanceRenderState<T> state);
    }
}

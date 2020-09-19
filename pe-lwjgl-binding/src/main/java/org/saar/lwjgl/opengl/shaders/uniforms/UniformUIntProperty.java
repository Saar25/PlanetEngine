package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public class UniformUIntProperty extends AbstractUniformProperty<Integer> {

    protected UniformUIntProperty(String name) {
        super(name);
    }

    @Override
    public void loadValue(Integer value) {
        load(value);
    }

    public void load(int value) {
        GL30.glUniform1ui(getLocation(), value);
    }

    public static abstract class Stage extends UniformUIntProperty implements UniformProperty.Stage<Integer> {
        public Stage(String name) {
            super(name);
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            load(getUniformValue(state));
        }

        public abstract int getUniformValue(StageRenderState state);
    }

    public static abstract class Instance<T> extends UniformUIntProperty implements UniformProperty.Instance<T, Integer> {
        public Instance(String name) {
            super(name);
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            load(getUniformValue(state));
        }

        public abstract int getUniformValue(InstanceRenderState<T> state);
    }

}

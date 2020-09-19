package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Matrix4fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public class UniformMat4Property extends UniformPropertyBase<Matrix4fc> {

    public UniformMat4Property(String name) {
        super(name);
    }

    @Override
    public void loadValue(Matrix4fc value) {
        final float[] buffer = value.get(new float[16]);
        GL20.glUniformMatrix4fv(getLocation(), false, buffer);
    }

    public static abstract class Stage extends UniformMat4Property implements UniformProperty.Stage<Matrix4fc> {
        public Stage(String name) {
            super(name);
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            loadValue(getUniformValue(state));
        }

        public abstract Matrix4fc getUniformValue(StageRenderState state);
    }

    public static abstract class Instance<T> extends UniformMat4Property implements UniformProperty.Instance<T, Matrix4fc> {
        public Instance(String name) {
            super(name);
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            loadValue(getUniformValue(state));
        }

        public abstract Matrix4fc getUniformValue(InstanceRenderState<T> state);
    }
}

package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector3fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public class UniformVec3Property extends UniformPropertyBase<Vector3fc> {

    public UniformVec3Property(String name) {
        super(name);
    }

    @Override
    public void loadValue(Vector3fc value) {
        load(value.x(), value.y(), value.z());
    }

    public void load(float x, float y, float z) {
        GL20.glUniform3f(getLocation(), x, y, z);
    }

    public static abstract class Stage extends UniformVec3Property implements UniformProperty.Stage<Vector3fc> {
        public Stage(String name) {
            super(name);
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            loadValue(getUniformValue(state));
        }

        public abstract Vector3fc getUniformValue(StageRenderState state);
    }

    public static abstract class Instance<T> extends UniformVec3Property implements UniformProperty.Instance<T, Vector3fc> {
        public Instance(String name) {
            super(name);
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            loadValue(getUniformValue(state));
        }

        public abstract Vector3fc getUniformValue(InstanceRenderState<T> state);
    }
}

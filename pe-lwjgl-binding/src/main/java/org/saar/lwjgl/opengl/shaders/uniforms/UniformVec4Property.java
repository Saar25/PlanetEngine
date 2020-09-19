package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector4fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public class UniformVec4Property extends UniformPropertyBase<Vector4fc> {

    public UniformVec4Property(String name) {
        super(name);
    }

    @Override
    public void loadValue(Vector4fc value) {
        load(value.x(), value.y(), value.z(), value.w());
    }

    public void load(float x, float y, float z, float w) {
        GL20.glUniform4f(getLocation(), x, y, z, w);
    }

    public static abstract class Stage extends UniformVec4Property implements UniformProperty.Stage<Vector4fc> {
        public Stage(String name) {
            super(name);
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            loadValue(getUniformValue(state));
        }

        public abstract Vector4fc getUniformValue(StageRenderState state);
    }

    public static abstract class Instance<T> extends UniformVec4Property implements UniformProperty.Instance<T, Vector4fc> {
        public Instance(String name) {
            super(name);
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            loadValue(getUniformValue(state));
        }

        public abstract Vector4fc getUniformValue(InstanceRenderState<T> state);
    }
}

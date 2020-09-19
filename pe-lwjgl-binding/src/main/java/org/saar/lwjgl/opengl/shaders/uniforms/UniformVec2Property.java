package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector2fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public class UniformVec2Property extends UniformPropertyBase<Vector2fc> {

    public UniformVec2Property(String name) {
        super(name);
    }

    @Override
    public void loadValue(Vector2fc value) {
        load(value.x(), value.y());
    }

    public void load(float x, float y) {
        GL20.glUniform2f(getLocation(), x, y);
    }

    public static abstract class Stage extends UniformVec2Property implements UniformProperty.Stage<Vector2fc> {
        public Stage(String name) {
            super(name);
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            loadValue(getUniformValue(state));
        }

        public abstract Vector2fc getUniformValue(StageRenderState state);
    }

    public static abstract class Instance<T> extends UniformVec2Property implements UniformProperty.Instance<T, Vector2fc> {
        public Instance(String name) {
            super(name);
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            loadValue(getUniformValue(state));
        }

        public abstract Vector2fc getUniformValue(InstanceRenderState<T> state);
    }

}

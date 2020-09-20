package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public abstract class IntUniform extends UniformBase implements Uniform {

    @Override
    public final void doLoad() {
        GL20.glUniform1i(getLocation(), getUniformValue());
    }

    public abstract int getUniformValue();

    public static abstract class Stage extends IntUniform implements StageUniform {

        private final String name;
        private int uniformValue = 0;

        public Stage(String name) {
            this.name = name;
        }

        @Override
        public final int getUniformValue() {
            return this.uniformValue;
        }

        @Override
        public final void update(StageRenderState state) {
            this.uniformValue = getUniformValue(state);
        }

        @Override
        public final String getName() {
            return this.name;
        }

        public abstract int getUniformValue(StageRenderState state);
    }

    public static abstract class Instance<T> extends IntUniform implements InstanceUniform<T> {

        private final String name;
        private int uniformValue = 0;

        public Instance(String name) {
            this.name = name;
        }

        @Override
        public final int getUniformValue() {
            return this.uniformValue;
        }

        @Override
        public void update(InstanceRenderState<T> state) {
            this.uniformValue = getUniformValue(state);
        }

        @Override
        public final String getName() {
            return this.name;
        }

        public abstract int getUniformValue(InstanceRenderState<T> state);
    }
}

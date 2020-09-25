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

    private static abstract class Base extends IntUniform {

        protected final String name;
        protected int uniformValue = 0;

        public Base(String name) {
            this.name = name;
        }

        @Override
        public final int getUniformValue() {
            return this.uniformValue;
        }

        @Override
        public final String getName() {
            return this.name;
        }
    }

    public static abstract class Stage extends Base implements StageUniform {

        public Stage(String name) {
            super(name);
        }

        @Override
        public final void update(StageRenderState state) {
            this.uniformValue = getUniformValue(state);
        }

        public abstract int getUniformValue(StageRenderState state);
    }

    public static abstract class Instance<T> extends Base implements InstanceUniform<T> {

        public Instance(String name) {
            super(name);
        }

        @Override
        public void update(InstanceRenderState<T> state) {
            this.uniformValue = getUniformValue(state);
        }

        public abstract int getUniformValue(InstanceRenderState<T> state);
    }
}

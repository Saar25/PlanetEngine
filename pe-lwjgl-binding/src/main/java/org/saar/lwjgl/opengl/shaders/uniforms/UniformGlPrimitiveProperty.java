package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.primitive.GlPrimitive;
import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

public class UniformGlPrimitiveProperty extends UniformPropertyBase<GlPrimitive> {

    public UniformGlPrimitiveProperty(String name) {
        super(name);
    }

    @Override
    public void loadValue(GlPrimitive value) {
        value.loadUniform(getLocation());
    }

    public static abstract class Stage extends UniformGlPrimitiveProperty implements UniformProperty.Stage<GlPrimitive> {
        public Stage(String name) {
            super(name);
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            loadValue(getUniformValue(state));
        }

        public abstract GlPrimitive getUniformValue(StageRenderState state);
    }

    public static abstract class Instance<T> extends UniformGlPrimitiveProperty implements UniformProperty.Instance<T, GlPrimitive> {
        public Instance(String name) {
            super(name);
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            loadValue(getUniformValue(state));
        }

        public abstract GlPrimitive getUniformValue(InstanceRenderState<T> state);
    }
}

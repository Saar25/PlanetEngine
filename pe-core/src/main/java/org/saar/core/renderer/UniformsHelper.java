package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;
import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class UniformsHelper<T> {

    public abstract void loadOnInstance(InstanceRenderState<T> state);

    public abstract void loadOnStage(StageRenderState state);

    public abstract UniformsHelper<T> addUniform(UniformProperty<T> property);

    public static <T> UniformsHelper<T> locate(Object object) {
        UniformsHelper<T> helper = empty();
        for (UniformProperty<?> property : Renderers.findUniformProperties(object)) {
            @SuppressWarnings("unchecked") final UniformProperty<T> cast =
                    (UniformProperty<T>) property;
            helper = helper.addUniform(cast);
        }
        return helper;
    }

    public static <T> UniformsHelper<T> empty() {
        @SuppressWarnings("unchecked") final UniformsHelper<T> empty =
                (UniformsHelper<T>) Empty.EMPTY;
        return empty;
    }

    private static class Empty<T> extends UniformsHelper<T> {

        private static final Empty<?> EMPTY = new Empty<>();

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
        }

        @Override
        public void loadOnStage(StageRenderState state) {
        }

        @Override
        public UniformsHelper<T> addUniform(UniformProperty<T> property) {
            return new UniformsHelper.Single<>(property);
        }
    }

    private static class Single<T> extends UniformsHelper<T> {

        private final UniformProperty<T> property;

        public Single(UniformProperty<T> property) {
            this.property = property;
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            this.property.loadOnInstance(state);
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            this.property.loadOnStage(state);
        }

        @Override
        public UniformsHelper<T> addUniform(UniformProperty<T> property) {
            return new UniformsHelper.Generic<>(this.property, property);
        }
    }

    private static class Generic<T> extends UniformsHelper<T> {

        private final List<UniformProperty<T>> properties;

        @SafeVarargs
        public Generic(UniformProperty<T>... properties) {
            this.properties = new ArrayList<>(Arrays.asList(properties));
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            for (UniformProperty<T> property : this.properties) {
                property.loadOnInstance(state);
            }
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            for (UniformProperty<T> property : this.properties) {
                property.loadOnStage(state);
            }
        }

        @Override
        public UniformsHelper<T> addUniform(UniformProperty<T> property) {
            this.properties.add(property);
            return this;
        }
    }

}

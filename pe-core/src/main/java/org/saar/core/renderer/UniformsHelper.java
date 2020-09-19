package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.StageRenderState;
import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class UniformsHelper<T> {

    public abstract void loadOnInstance(InstanceRenderState<T> state);

    public abstract void loadOnStage(StageRenderState state);

    public abstract UniformsHelper<T> addUniform(UniformProperty.Instance<T, ?> property);

    public abstract UniformsHelper<T> addUniform(UniformProperty.Stage<T> property);

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
        public UniformsHelper<T> addUniform(UniformProperty.Instance<T, ?> property) {
            return new OnlyInstance<>(property);
        }

        @Override
        public UniformsHelper<T> addUniform(UniformProperty.Stage<T> property) {
            return new OnlyStage<>(property);
        }
    }

    private static class OnlyStage<T> extends UniformsHelper<T> {

        private final List<UniformProperty.Stage<T>> properties;

        public OnlyStage(UniformProperty.Stage<T> property) {
            this.properties = new ArrayList<>();
            this.properties.add(property);
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            for (UniformProperty.Stage<T> property : this.properties) {
                property.loadOnStage(state);
            }
        }

        @Override
        public UniformsHelper<T> addUniform(UniformProperty.Instance<T, ?> property) {
            return new Generic<>(this, property);
        }

        @Override
        public UniformsHelper<T> addUniform(UniformProperty.Stage<T> property) {
            this.properties.add(property);
            return this;
        }
    }

    private static class OnlyInstance<T> extends UniformsHelper<T> {

        private final List<UniformProperty.Instance<T, ?>> properties;

        public OnlyInstance(UniformProperty.Instance<T, ?> property) {
            this.properties = new ArrayList<>();
            this.properties.add(property);
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            for (UniformProperty.Instance<T, ?> property : this.properties) {
                property.loadOnInstance(state);
            }
        }

        @Override
        public void loadOnStage(StageRenderState state) {
        }

        @Override
        public UniformsHelper<T> addUniform(UniformProperty.Instance<T, ?> property) {
            this.properties.add(property);
            return this;
        }

        @Override
        public UniformsHelper<T> addUniform(UniformProperty.Stage<T> property) {
            return new Generic<>(this, property);
        }
    }

    private static class Generic<T> extends UniformsHelper<T> {

        private final OnlyStage<T> stageHelper;
        private final OnlyInstance<T> instanceHelper;

        public Generic(OnlyStage<T> stageHelper, UniformProperty.Instance<T, ?> instance) {
            this.stageHelper = stageHelper;
            this.instanceHelper = new OnlyInstance<>(instance);
        }

        public Generic(OnlyInstance<T> instanceHelper, UniformProperty.Stage<T> stage) {
            this.instanceHelper = instanceHelper;
            this.stageHelper = new OnlyStage<>(stage);
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            this.instanceHelper.loadOnInstance(state);
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            this.stageHelper.loadOnStage(state);
        }

        @Override
        public UniformsHelper<T> addUniform(UniformProperty.Instance<T, ?> property) {
            this.instanceHelper.addUniform(property);
            return this;
        }

        @Override
        public UniformsHelper<T> addUniform(UniformProperty.Stage<T> property) {
            this.stageHelper.addUniform(property);
            return this;
        }
    }

}

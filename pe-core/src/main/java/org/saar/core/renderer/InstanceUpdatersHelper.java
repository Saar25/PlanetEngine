package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.InstanceRenderState;

import java.util.ArrayList;
import java.util.List;

public abstract class InstanceUpdatersHelper<T> {

    public abstract InstanceUpdatersHelper<T> addUpdater(InstanceUniformUpdater<T> uniform);

    public abstract InstanceUpdatersHelper<T> removeUpdaters(InstanceUniformUpdater<T> uniform);

    public abstract void update(InstanceRenderState<T> state);

    public static <T> InstanceUpdatersHelper<T> empty() {
        @SuppressWarnings("unchecked") final InstanceUpdatersHelper<T> empty =
                (InstanceUpdatersHelper<T>) Empty.EMPTY;
        return empty;
    }

    private static class Empty<T> extends InstanceUpdatersHelper<T> {

        private static final Empty<?> EMPTY = new Empty<>();

        @Override
        public InstanceUpdatersHelper<T> addUpdater(InstanceUniformUpdater<T> uniform) {
            return new Generic<>(uniform);
        }

        public InstanceUpdatersHelper<T> removeUpdaters(InstanceUniformUpdater<T> uniform) {
            return this;
        }

        @Override
        public void update(InstanceRenderState<T> state) {
        }
    }

    private static class Generic<T> extends InstanceUpdatersHelper<T> {

        private final List<InstanceUniformUpdater<T>> uniforms = new ArrayList<>();

        public Generic(InstanceUniformUpdater<T> uniform) {
            this.uniforms.add(uniform);
        }

        @Override
        public InstanceUpdatersHelper<T> addUpdater(InstanceUniformUpdater<T> uniform) {
            this.uniforms.add(uniform);
            return this;
        }

        public InstanceUpdatersHelper<T> removeUpdaters(InstanceUniformUpdater<T> uniform) {
            this.uniforms.remove(uniform);

            return this.uniforms.size() > 0 ? this : empty();
        }

        @Override
        public void update(InstanceRenderState<T> state) {
            for (InstanceUniformUpdater<T> uniform : this.uniforms) {
                uniform.update(state);
            }
        }
    }

}

package org.saar.core.renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class UpdatersHelper<T> {

    public abstract UpdatersHelper<T> addUpdater(UniformUpdater<T> uniform);

    public abstract UpdatersHelper<T> removeUpdaters(UniformUpdater<T> uniform);

    public abstract void update(RenderState<T> state);

    public static <T> UpdatersHelper<T> empty() {
        @SuppressWarnings("unchecked") final UpdatersHelper<T> empty =
                (UpdatersHelper<T>) Empty.EMPTY;
        return empty;
    }

    private static class Empty<T> extends UpdatersHelper<T> {

        private static final Empty<?> EMPTY = new Empty<>();

        @Override
        public UpdatersHelper<T> addUpdater(UniformUpdater<T> uniform) {
            return new Generic<>(uniform);
        }

        public UpdatersHelper<T> removeUpdaters(UniformUpdater<T> uniform) {
            return this;
        }

        @Override
        public void update(RenderState<T> state) {
        }
    }

    private static class Generic<T> extends UpdatersHelper<T> {

        private final List<UniformUpdater<T>> uniforms = new ArrayList<>();

        public Generic(UniformUpdater<T> uniform) {
            this.uniforms.add(uniform);
        }

        @Override
        public UpdatersHelper<T> addUpdater(UniformUpdater<T> uniform) {
            this.uniforms.add(uniform);
            return this;
        }

        public UpdatersHelper<T> removeUpdaters(UniformUpdater<T> uniform) {
            this.uniforms.remove(uniform);

            return this.uniforms.size() > 0 ? this : empty();
        }

        @Override
        public void update(RenderState<T> state) {
            for (UniformUpdater<T> uniform : this.uniforms) {
                uniform.update(state);
            }
        }
    }

}

package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.StageRenderState;

import java.util.ArrayList;
import java.util.List;

public abstract class StageUpdatersHelper {

    public abstract StageUpdatersHelper addUpdater(StageUniformUpdater uniform);

    public abstract StageUpdatersHelper removeUpdaters(StageUniformUpdater uniform);

    public abstract void update(StageRenderState state);

    public static StageUpdatersHelper empty() {
        return Empty.EMPTY;
    }

    private static class Empty extends StageUpdatersHelper {

        private static final Empty EMPTY = new Empty();

        @Override
        public StageUpdatersHelper addUpdater(StageUniformUpdater uniform) {
            return new Generic(uniform);
        }

        public StageUpdatersHelper removeUpdaters(StageUniformUpdater uniform) {
            return this;
        }

        @Override
        public void update(StageRenderState state) {
        }
    }

    private static class Generic extends StageUpdatersHelper {

        private final List<StageUniformUpdater> uniforms = new ArrayList<>();

        public Generic(StageUniformUpdater uniform) {
            this.uniforms.add(uniform);
        }

        @Override
        public StageUpdatersHelper addUpdater(StageUniformUpdater uniform) {
            this.uniforms.add(uniform);
            return this;
        }

        public StageUpdatersHelper removeUpdaters(StageUniformUpdater uniform) {
            this.uniforms.remove(uniform);

            return this.uniforms.size() > 0 ? this : empty();
        }

        @Override
        public void update(StageRenderState state) {
            for (StageUniformUpdater uniform : this.uniforms) {
                uniform.update(state);
            }
        }
    }

}

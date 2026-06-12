package org.saar.core.renderer.uniforms;

import org.saar.lwjgl.opengl.shader.uniforms.UniformWrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class UniformsHelper {

    public static UniformsHelper empty() {
        return Empty.EMPTY;
    }

    public abstract UniformsHelper addUniform(UniformWrapper uniform);

    public abstract void load();

    private static class Empty extends UniformsHelper {

        private static final Empty EMPTY = new Empty();

        @Override
        public UniformsHelper addUniform(UniformWrapper uniform) {
            final UniformsHelper helper = new Generic();
            return helper.addUniform(uniform);
        }

        @Override
        public void load() {
        }
    }

    private static class Generic extends UniformsHelper {

        private final List<UniformWrapper> uniforms = new ArrayList<>();

        @Override
        public UniformsHelper addUniform(UniformWrapper uniform) {
            this.uniforms.add(uniform);
            return this;
        }

        @Override
        public void load() {
            this.uniforms.forEach(UniformWrapper::load);
        }
    }

}

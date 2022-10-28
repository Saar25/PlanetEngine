package org.saar.core.renderer.uniforms;

import org.saar.lwjgl.opengl.shader.uniforms.UniformWrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class UniformsHelper {

    public static UniformsHelper empty() {
        return Empty.EMPTY;
    }

    public abstract UniformsHelper addUniform(UniformWrapper uniform);

    public abstract UniformsHelper addPerInstanceUniform(UniformWrapper uniform);

    public abstract UniformsHelper addPerRenderCycleUniform(UniformWrapper uniform);

    public abstract void load();

    public abstract void loadPerInstance();

    public abstract void loadPerRenderCycle();

    private static class Empty extends UniformsHelper {

        private static final Empty EMPTY = new Empty();

        @Override
        public UniformsHelper addUniform(UniformWrapper uniform) {
            final UniformsHelper helper = new Generic();
            return helper.addUniform(uniform);
        }

        @Override
        public UniformsHelper addPerInstanceUniform(UniformWrapper uniform) {
            final UniformsHelper helper = new Generic();
            return helper.addPerInstanceUniform(uniform);
        }

        @Override
        public UniformsHelper addPerRenderCycleUniform(UniformWrapper uniform) {
            final UniformsHelper helper = new Generic();
            return helper.addPerRenderCycleUniform(uniform);
        }

        @Override
        public void load() {
        }

        @Override
        public void loadPerInstance() {

        }

        @Override
        public void loadPerRenderCycle() {

        }
    }

    private static class Generic extends UniformsHelper {

        private final List<UniformWrapper> alwaysUniforms = new ArrayList<>();
        private final List<UniformWrapper> perInstanceUniforms = new ArrayList<>();
        private final List<UniformWrapper> perRenderCycleUniforms = new ArrayList<>();

        @Override
        public UniformsHelper addUniform(UniformWrapper uniform) {
            this.alwaysUniforms.add(uniform);
            return this;
        }

        @Override
        public UniformsHelper addPerInstanceUniform(UniformWrapper uniform) {
            this.perInstanceUniforms.add(uniform);
            return this;
        }

        @Override
        public UniformsHelper addPerRenderCycleUniform(UniformWrapper uniform) {
            this.perRenderCycleUniforms.add(uniform);
            return this;
        }

        @Override
        public void load() {
            this.alwaysUniforms.forEach(UniformWrapper::load);
        }

        @Override
        public void loadPerInstance() {
            this.alwaysUniforms.forEach(UniformWrapper::load);
            this.perInstanceUniforms.forEach(UniformWrapper::load);
        }

        @Override
        public void loadPerRenderCycle() {
            this.alwaysUniforms.forEach(UniformWrapper::load);
            this.perRenderCycleUniforms.forEach(UniformWrapper::load);
        }
    }

}

package org.saar.core.renderer.uniforms;

import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.uniforms.Uniform;

import java.util.ArrayList;
import java.util.List;

public abstract class UniformsHelper {

    public static UniformsHelper empty() {
        return Empty.EMPTY;
    }

    public abstract UniformsHelper addUniform(Uniform uniform);

    public abstract UniformsHelper addPerInstanceUniform(Uniform uniform);

    public abstract UniformsHelper addPerRenderCycleUniform(Uniform uniform);

    public abstract void initialize(ShadersProgram shadersProgram);

    public abstract void load();

    public abstract void loadPerInstance();

    public abstract void loadPerRenderCycle();

    private static class Empty extends UniformsHelper {

        private static final Empty EMPTY = new Empty();

        @Override
        public UniformsHelper addUniform(Uniform uniform) {
            final UniformsHelper helper = new Generic();
            return helper.addUniform(uniform);
        }

        @Override
        public UniformsHelper addPerInstanceUniform(Uniform uniform) {
            final UniformsHelper helper = new Generic();
            return helper.addPerInstanceUniform(uniform);
        }

        @Override
        public UniformsHelper addPerRenderCycleUniform(Uniform uniform) {
            final UniformsHelper helper = new Generic();
            return helper.addPerRenderCycleUniform(uniform);
        }

        @Override
        public void initialize(ShadersProgram shadersProgram) {
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

        private final List<Uniform> alwaysUniforms = new ArrayList<>();
        private final List<Uniform> perInstanceUniforms = new ArrayList<>();
        private final List<Uniform> perRenderCycleUniforms = new ArrayList<>();

        @Override
        public UniformsHelper addUniform(Uniform uniform) {
            this.alwaysUniforms.add(uniform);
            return this;
        }

        @Override
        public UniformsHelper addPerInstanceUniform(Uniform uniform) {
            this.perInstanceUniforms.add(uniform);
            return this;
        }

        @Override
        public UniformsHelper addPerRenderCycleUniform(Uniform uniform) {
            this.perRenderCycleUniforms.add(uniform);
            return this;
        }

        @Override
        public void initialize(ShadersProgram shadersProgram) {
            for (Uniform uniform : this.alwaysUniforms) {
                uniform.initialize(shadersProgram);
            }
            for (Uniform uniform : this.perInstanceUniforms) {
                uniform.initialize(shadersProgram);
            }
            for (Uniform uniform : this.perRenderCycleUniforms) {
                uniform.initialize(shadersProgram);
            }
        }

        @Override
        public void load() {
            this.alwaysUniforms.forEach(Uniform::load);
        }

        @Override
        public void loadPerInstance() {
            this.alwaysUniforms.forEach(Uniform::load);
            this.perInstanceUniforms.forEach(Uniform::load);
        }

        @Override
        public void loadPerRenderCycle() {
            this.alwaysUniforms.forEach(Uniform::load);
            this.perRenderCycleUniforms.forEach(Uniform::load);
        }
    }

}

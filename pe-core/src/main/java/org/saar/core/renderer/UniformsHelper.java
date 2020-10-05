package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.uniforms.Uniform;

import java.util.ArrayList;
import java.util.List;

public abstract class UniformsHelper {

    public abstract UniformsHelper addUniform(Uniform uniform);

    public abstract UniformsHelper removeUniform(Uniform uniform);

    public abstract void initialize(ShadersProgram shadersProgram);

    public abstract void load();

    public static UniformsHelper empty() {
        return Empty.EMPTY;
    }

    private static class Empty extends UniformsHelper {

        private static final Empty EMPTY = new Empty();

        @Override
        public UniformsHelper addUniform(Uniform uniform) {
            return new Generic(uniform);
        }

        public UniformsHelper removeUniform(Uniform uniform) {
            return this;
        }

        @Override
        public void initialize(ShadersProgram shadersProgram) {
        }

        @Override
        public void load() {
        }
    }

    private static class Generic extends UniformsHelper {

        private final List<Uniform> uniforms = new ArrayList<>();

        public Generic(Uniform uniform) {
            this.uniforms.add(uniform);
        }

        @Override
        public UniformsHelper addUniform(Uniform uniform) {
            this.uniforms.add(uniform);
            return this;
        }

        public UniformsHelper removeUniform(Uniform uniform) {
            this.uniforms.remove(uniform);

            return this.uniforms.size() > 0 ? this : empty();
        }

        @Override
        public void initialize(ShadersProgram shadersProgram) {
            for (Uniform uniform : this.uniforms) {
                uniform.initialize(shadersProgram);
            }
        }

        @Override
        public void load() {
            for (Uniform uniform : this.uniforms) {
                uniform.load();
            }
        }
    }

}

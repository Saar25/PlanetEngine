package org.saar.core.renderer.shaders;

import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShaderCompileException;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;

import java.util.ArrayList;
import java.util.List;

public abstract class ShadersHelper {

    public static ShadersHelper empty() {
        return Empty.EMPTY;
    }

    public abstract ShadersHelper addShader(Shader shader);

    public abstract ShadersHelper removeShader(Shader shader);

    public abstract ShadersProgram createProgram() throws ShaderCompileException;

    private static class Empty extends ShadersHelper {

        private static final Empty EMPTY = new Empty();

        @Override
        public ShadersHelper addShader(Shader shader) {
            return new Generic(shader);
        }

        @Override
        public ShadersHelper removeShader(Shader shader) {
            return this;
        }

        @Override
        public ShadersProgram createProgram() throws ShaderCompileException {
            throw new ShaderCompileException("no shaders found");
        }
    }

    private static class Generic extends ShadersHelper {

        private final List<Shader> shaders = new ArrayList<>();

        public Generic(Shader shader) {
            this.shaders.add(shader);
        }

        @Override
        public ShadersHelper addShader(Shader shader) {
            this.shaders.add(shader);
            return this;
        }

        @Override
        public ShadersHelper removeShader(Shader shader) {
            this.shaders.remove(shader);

            return this.shaders.size() > 0 ? this : empty();
        }

        @Override
        public ShadersProgram createProgram() throws ShaderCompileException {
            return ShadersProgram.create(this.shaders.toArray(new Shader[0]));
        }
    }

}

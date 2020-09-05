package org.saar.lwjgl.opengl.shaders;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ShadersProgram {

    private static int boundProgram = 0;

    private final int id;

    private int attributeCount = 0;
    private boolean deleted = false;

    private ShadersProgram(int id, Shader... shaders) throws Exception {
        this.id = id;

        bind();
        for (Shader shader : shaders) {
            shader.init();
            shader.attach(id);
        }
        GL20.glLinkProgram(id);
        GL20.glValidateProgram(id);
        for (Shader shader : shaders) {
            shader.detach(id);
            shader.delete();
        }
        unbind();
    }

    public static ShadersProgram create(Shader vertexShader, Shader fragmentShader) throws Exception {
        final int id = GL20.glCreateProgram();
        return new ShadersProgram(id, vertexShader, fragmentShader);
    }

    public static ShadersProgram create(String vertexFile, String fragmentFile) throws Exception {
        final Shader vertexShader = Shader.createVertex(vertexFile);
        final Shader fragmentShader = Shader.createFragment(fragmentFile);
        return ShadersProgram.create(vertexShader, fragmentShader);
    }

    public void bindAttribute(int location, String name) {
        GL20.glBindAttribLocation(id, location, name);
    }

    public void bindAttribute(String name) {
        GL20.glBindAttribLocation(id, attributeCount++, name);
    }

    public void bindAttributes(String... names) {
        for (String name : names) {
            bindAttribute(name);
        }
    }

    public void bindFragmentOutput(int location, String name) {
        GL30.glBindFragDataLocation(this.id, location, name);
    }

    public void bindFragmentOutputs(String... names) {
        for (int i = 0; i < names.length; i++) {
            bindFragmentOutput(i, names[i]);
        }
    }

    public int getUniformLocation(String name) {
        this.bind0();
        return GL20.glGetUniformLocation(id, name);
    }

    public void bind() {
        if (boundProgram != id) {
            boundProgram = id;
            bind0();
        }
    }

    private void bind0() {
        GL20.glUseProgram(id);
    }

    public void unbind() {
        if (boundProgram != 0) {
            GL20.glUseProgram(0);
            boundProgram = 0;
        }
    }

    public void delete() {
        if (!deleted) {
            GL20.glDeleteProgram(id);
            deleted = true;
        }
    }

}

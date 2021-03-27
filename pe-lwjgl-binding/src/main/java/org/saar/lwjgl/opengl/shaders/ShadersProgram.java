package org.saar.lwjgl.opengl.shaders;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.utils.GlConfigs;

public class ShadersProgram {

    private final int id;

    private int attributeCount = 0;
    private boolean deleted = false;

    private ShadersProgram(int id, Shader... shaders) throws ShaderCompileException {
        this.id = id;

//        bind();
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

    public static ShadersProgram create(Shader vertexShader, Shader fragmentShader) throws ShaderCompileException {
        final int id = GL20.glCreateProgram();
        return new ShadersProgram(id, vertexShader, fragmentShader);
    }

    public static ShadersProgram create(Shader... shaders) throws ShaderCompileException {
        final int id = GL20.glCreateProgram();
        return new ShadersProgram(id, shaders);
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
        return GL20.glGetUniformLocation(this.id, name);
    }

    public void bind() {
        if (!GlConfigs.CACHE_STATE || !BoundProgram.isBound(this.id)) {
            GL20.glUseProgram(this.id);
            BoundProgram.set(this.id);
        }
    }

    public void unbind() {
        if (!GlConfigs.CACHE_STATE || !BoundProgram.isBound(0)) {
            GL20.glUseProgram(0);
            BoundProgram.set(0);
        }
    }

    public void delete() {
        if (!this.deleted) {
            GL20.glDeleteProgram(id);
            this.deleted = true;
        }
    }

}

package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.RenderState;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.textures.ITexture;
import org.saar.lwjgl.opengl.textures.Texture;

public abstract class UniformTextureProperty<T> implements UniformProperty<T> {

    private final String name;
    private final int unit;

    public UniformTextureProperty(String name, int unit) {
        this.name = name;
        this.unit = unit;
    }

    @Override
    public void load(RenderState<T> state) {
        if (valueAvailable()) {
            Texture.bind(getUniformValue(state), unit);
        }
    }

    @Override
    public void initialize(ShadersProgram shadersProgram) {
        if (name.charAt(0) != '#') {
            GL20.glUniform1i(shadersProgram.getUniformLocation(name), unit);
        }
    }

    public abstract ITexture getUniformValue(RenderState<T> state);
}

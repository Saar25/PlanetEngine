package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.lwjgl.opengl.textures.Texture;

public class TextureUniformValue extends TextureUniform implements UniformValue<ReadOnlyTexture> {

    private final String name;
    private final int unit;

    private ReadOnlyTexture value = Texture.NULL;

    public TextureUniformValue(String name, int unit) {
        this.name = name;
        this.unit = unit;
    }

    @Override
    public final ReadOnlyTexture getUniformValue() {
        return getValue();
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final int getUnit() {
        return this.unit;
    }

    @Override
    public final ReadOnlyTexture getValue() {
        return this.value;
    }

    @Override
    public final void setValue(ReadOnlyTexture value) {
        this.value = value;
    }
}

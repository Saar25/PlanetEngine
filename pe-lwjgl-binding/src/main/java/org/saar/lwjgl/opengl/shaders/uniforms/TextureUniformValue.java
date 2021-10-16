package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.texture.ReadOnlyTexture;
import org.saar.lwjgl.opengl.texture.Texture2D;

public class TextureUniformValue extends TextureUniform implements UniformValue<ReadOnlyTexture> {

    private final String name;
    private final int unit;

    private ReadOnlyTexture value = Texture2D.NULL;

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

package org.saar.lwjgl.opengl.texture.parameter;

import org.lwjgl.opengl.GL14;

public class TextureLodBiasParameter extends TextureFloatParameterParameter implements TextureParameter {

    private static final int pName = GL14.GL_TEXTURE_LOD_BIAS;

    public TextureLodBiasParameter(float lodBias) {
        super(TextureLodBiasParameter.pName, lodBias);
    }
}

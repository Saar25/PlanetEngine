package org.saar.lwjgl.opengl.texture.parameter;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.opengl.texture.values.MagFilterValue;

public class TextureMagFilterParameter extends TextureIntParameterParameter implements TextureParameter {

    private static final int pName = GL11.GL_TEXTURE_MAG_FILTER;

    public TextureMagFilterParameter(MagFilterValue magFilter) {
        super(TextureMagFilterParameter.pName, magFilter.get());
    }
}

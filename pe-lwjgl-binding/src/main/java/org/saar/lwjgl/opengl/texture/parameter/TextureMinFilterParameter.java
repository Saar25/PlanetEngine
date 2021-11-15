package org.saar.lwjgl.opengl.texture.parameter;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.opengl.texture.values.MinFilterValue;

public class TextureMinFilterParameter extends TextureIntParameterParameter implements TextureParameter {

    private static final int pName = GL11.GL_TEXTURE_MIN_FILTER;

    public TextureMinFilterParameter(MinFilterValue minFilter) {
        super(TextureMinFilterParameter.pName, minFilter.get());
    }
}

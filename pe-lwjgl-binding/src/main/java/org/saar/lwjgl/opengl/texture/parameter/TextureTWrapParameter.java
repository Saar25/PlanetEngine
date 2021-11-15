package org.saar.lwjgl.opengl.texture.parameter;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.opengl.texture.values.WrapValue;

public class TextureTWrapParameter extends TextureIntParameterParameter implements TextureParameter {

    private static final int pName = GL11.GL_TEXTURE_WRAP_T;

    public TextureTWrapParameter(WrapValue wrap) {
        super(TextureTWrapParameter.pName, wrap.get());
    }
}

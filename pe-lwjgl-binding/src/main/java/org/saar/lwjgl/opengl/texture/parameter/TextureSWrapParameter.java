package org.saar.lwjgl.opengl.texture.parameter;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.opengl.texture.values.WrapValue;

public class TextureSWrapParameter extends TextureIntParameterParameter implements TextureParameter {

    private static final int pName = GL11.GL_TEXTURE_WRAP_S;

    public TextureSWrapParameter(WrapValue wrap) {
        super(TextureSWrapParameter.pName, wrap.get());
    }
}

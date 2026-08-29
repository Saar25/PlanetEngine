package org.saar.lwjgl.opengl.texture.parameter;

import org.lwjgl.opengl.GL11;

public class TextureBorderColorParameter extends TextureFloatsParameterParameter implements TextureParameter {

    private static final int pName = GL11.GL_TEXTURE_BORDER_COLOR;

    public TextureBorderColorParameter(float r, float g, float b, float a) {
        super(TextureBorderColorParameter.pName, r, g, b, a);
    }
}

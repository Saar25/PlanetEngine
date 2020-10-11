package org.saar.lwjgl.opengl.textures.settings;

import org.lwjgl.opengl.GL11;

public class TextureBorderColourSetting extends TextureFloatsParameterSetting implements TextureSetting {

    private static final int pName = GL11.GL_TEXTURE_BORDER_COLOR;

    public TextureBorderColourSetting(float r, float g, float b, float a) {
        super(TextureBorderColourSetting.pName, r, g, b, a);
    }
}

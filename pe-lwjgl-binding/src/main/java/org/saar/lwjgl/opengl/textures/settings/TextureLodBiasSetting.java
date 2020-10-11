package org.saar.lwjgl.opengl.textures.settings;

import org.lwjgl.opengl.GL11;

public class TextureLodBiasSetting extends TextureFloatParameterSetting implements TextureSetting {

    private static final int pName = GL11.GL_TEXTURE_BORDER_COLOR;

    public TextureLodBiasSetting(float lodBias) {
        super(TextureLodBiasSetting.pName, lodBias);
    }
}

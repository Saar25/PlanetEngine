package org.saar.lwjgl.opengl.textures.settings;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;

public class TextureMagFilterSetting extends TextureIntParameterSetting implements TextureSetting {

    private static final int pName = GL11.GL_TEXTURE_MAG_FILTER;

    public TextureMagFilterSetting(MagFilterParameter magFilter) {
        super(TextureMagFilterSetting.pName, magFilter.get());
    }
}

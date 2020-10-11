package org.saar.lwjgl.opengl.textures.settings;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.opengl.textures.parameters.MinFilterParameter;

public class TextureMinFilterSetting extends TextureIntParameterSetting implements TextureSetting {

    private static final int pName = GL11.GL_TEXTURE_MIN_FILTER;

    public TextureMinFilterSetting(MinFilterParameter minFilter) {
        super(TextureMinFilterSetting.pName, minFilter.get());
    }
}

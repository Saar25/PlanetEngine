package org.saar.lwjgl.opengl.textures.settings;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.opengl.textures.parameters.WrapParameter;

public class TextureTWrapSetting extends TextureIntParameterSetting implements TextureSetting {

    private static final int pName = GL11.GL_TEXTURE_WRAP_T;

    public TextureTWrapSetting(WrapParameter wrap) {
        super(TextureTWrapSetting.pName, wrap.get());
    }
}

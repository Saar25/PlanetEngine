package org.saar.lwjgl.opengl.textures.settings;

import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.textures.TextureTarget;

public class TextureMipMapSetting implements TextureSetting {

    @Override
    public void apply(TextureTarget target) {
        GL30.glGenerateMipmap(target.get());
    }
}

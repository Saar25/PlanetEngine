package org.saar.lwjgl.opengl.textures.settings;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.opengl.textures.TextureTarget;

abstract class TextureIntParameterSetting implements TextureSetting {

    private final int name;
    private final int value;

    TextureIntParameterSetting(int name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public final void apply(TextureTarget target) {
        GL11.glTexParameteri(target.get(), this.name, this.value);
    }
}

package org.saar.lwjgl.opengl.textures.settings;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.opengl.textures.TextureTarget;

abstract class TextureFloatParameterSetting implements TextureSetting {

    private final int name;
    private final float value;

    TextureFloatParameterSetting(int name, float value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public final void apply(TextureTarget target) {
        GL11.glTexParameterf(target.get(), this.name, this.value);
    }
}

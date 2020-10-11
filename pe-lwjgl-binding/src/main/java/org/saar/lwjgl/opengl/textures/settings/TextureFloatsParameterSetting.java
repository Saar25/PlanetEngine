package org.saar.lwjgl.opengl.textures.settings;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.opengl.textures.TextureTarget;

abstract class TextureFloatsParameterSetting implements TextureSetting {

    private final int name;
    private final float[] value;

    TextureFloatsParameterSetting(int name, float... value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public final void apply(TextureTarget target) {
        GL11.glTexParameterfv(target.get(), this.name, this.value);
    }
}

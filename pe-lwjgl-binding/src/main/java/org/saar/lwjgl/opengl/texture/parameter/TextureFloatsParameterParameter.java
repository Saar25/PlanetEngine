package org.saar.lwjgl.opengl.texture.parameter;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.opengl.texture.TextureTarget;

abstract class TextureFloatsParameterParameter implements TextureParameter {

    private final int name;
    private final float[] value;

    TextureFloatsParameterParameter(int name, float... value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public final void apply(TextureTarget target) {
        GL11.glTexParameterfv(target.get(), this.name, this.value);
    }
}

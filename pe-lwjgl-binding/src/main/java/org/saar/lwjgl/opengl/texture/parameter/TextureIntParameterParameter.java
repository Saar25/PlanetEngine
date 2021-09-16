package org.saar.lwjgl.opengl.texture.parameter;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.opengl.texture.TextureTarget;

abstract class TextureIntParameterParameter implements TextureParameter {

    private final int name;
    private final int value;

    TextureIntParameterParameter(int name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public final void apply(TextureTarget target) {
        GL11.glTexParameteri(target.get(), this.name, this.value);
    }
}

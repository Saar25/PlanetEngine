package org.saar.lwjgl.opengl.texture.values;

import org.lwjgl.opengl.GL11;

public enum MagFilterValue {

    NEAREST(GL11.GL_NEAREST),
    LINEAR(GL11.GL_LINEAR);

    private final int value;

    MagFilterValue(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }
}

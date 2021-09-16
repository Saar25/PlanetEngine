package org.saar.lwjgl.opengl.texture.values;

import org.lwjgl.opengl.GL11;

public enum MinFilterValue {

    NEAREST(GL11.GL_NEAREST),
    LINEAR(GL11.GL_LINEAR),

    NEAREST_MIPMAP_NEAREST(GL11.GL_NEAREST_MIPMAP_NEAREST),
    LINEAR_MIPMAP_NEAREST(GL11.GL_LINEAR_MIPMAP_NEAREST),
    NEAREST_MIPMAP_LINEAR(GL11.GL_NEAREST_MIPMAP_LINEAR),
    LINEAR_MIPMAP_LINEAR(GL11.GL_LINEAR_MIPMAP_LINEAR);

    private final int value;

    MinFilterValue(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }
}

package org.saar.lwjgl.opengl.fbo;

import org.lwjgl.opengl.GL11;

public enum FboBlitFilter {

    NEAREST(GL11.GL_NEAREST),
    LINEAR(GL11.GL_LINEAR);

    private final int value;

    FboBlitFilter(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }
}

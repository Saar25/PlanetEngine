package org.saar.lwjgl.opengl.utils;

import org.lwjgl.opengl.GL11;

public enum GlCullFace {

    NONE(GL11.GL_NONE),
    FRONT(GL11.GL_FRONT),
    BACK(GL11.GL_BACK),
    FRONT_AND_BACK(GL11.GL_FRONT_AND_BACK),
    ;

    private final int value;

    GlCullFace(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }
}

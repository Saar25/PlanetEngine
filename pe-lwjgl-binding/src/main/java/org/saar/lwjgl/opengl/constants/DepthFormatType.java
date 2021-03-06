package org.saar.lwjgl.opengl.constants;

import org.lwjgl.opengl.GL14;

public enum DepthFormatType implements IInternalFormat {

    COMPONENT16(GL14.GL_DEPTH_COMPONENT16),
    COMPONENT24(GL14.GL_DEPTH_COMPONENT24),
    COMPONENT32(GL14.GL_DEPTH_COMPONENT32),
    ;

    private final int value;

    DepthFormatType(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }

}

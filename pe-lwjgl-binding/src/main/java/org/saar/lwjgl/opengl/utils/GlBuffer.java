package org.saar.lwjgl.opengl.utils;

import org.lwjgl.opengl.GL11;

import java.util.Arrays;

public enum GlBuffer {

    COLOUR(GL11.GL_COLOR_BUFFER_BIT),
    DEPTH(GL11.GL_DEPTH_BUFFER_BIT),
    STENCIL(GL11.GL_STENCIL_BUFFER_BIT),
    ;

    private final int value;

    GlBuffer(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public static int getValue(GlBuffer... buffers) {
        return getValue(Arrays.asList(buffers));
    }

    public static int getValue(Iterable<GlBuffer> buffers) {
        int buffersValue = 0;
        for (GlBuffer buffer : buffers) {
            buffersValue |= buffer.get();
        }
        return buffersValue;
    }
}

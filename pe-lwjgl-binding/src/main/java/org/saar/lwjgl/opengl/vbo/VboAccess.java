package org.saar.lwjgl.opengl.vbo;

import org.saar.lwjgl.opengl.buffer.BufferAccess;

public enum VboAccess {

    READ_ONLY(BufferAccess.READ_ONLY),
    WRITE_ONLY(BufferAccess.WRITE_ONLY),
    READ_WRITE(BufferAccess.READ_WRITE);

    private final BufferAccess value;

    VboAccess(BufferAccess value) {
        this.value = value;
    }

    public BufferAccess get() {
        return value;
    }
}

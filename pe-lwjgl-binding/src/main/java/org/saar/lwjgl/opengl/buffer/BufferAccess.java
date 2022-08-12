package org.saar.lwjgl.opengl.buffer;

import org.lwjgl.opengl.GL15;

public enum BufferAccess {

    READ_ONLY(GL15.GL_READ_ONLY),
    WRITE_ONLY(GL15.GL_WRITE_ONLY),
    READ_WRITE(GL15.GL_READ_WRITE),
    ;

    private final int value;

    BufferAccess(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }
}

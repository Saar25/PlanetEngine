package org.saar.lwjgl.opengl.objects.vbos;

import org.saar.lwjgl.opengl.objects.buffers.BufferAccess;

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

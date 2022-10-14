package org.saar.lwjgl.opengl.vbo;

import org.saar.lwjgl.opengl.buffer.BufferTarget;

public enum VboTarget {

    ARRAY_BUFFER(BufferTarget.ARRAY),
    ELEMENT_ARRAY_BUFFER(BufferTarget.ELEMENT_ARRAY),
    ;

    private final BufferTarget value;

    VboTarget(BufferTarget value) {
        this.value = value;
    }

    public BufferTarget get() {
        return this.value;
    }

}

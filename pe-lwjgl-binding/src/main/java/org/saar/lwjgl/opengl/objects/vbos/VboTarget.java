package org.saar.lwjgl.opengl.objects.vbos;

import org.saar.lwjgl.opengl.objects.buffers.BufferTarget;

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

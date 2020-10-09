package org.saar.lwjgl.opengl.objects.vbos;

import org.saar.lwjgl.opengl.objects.buffers.BufferUsage;

public enum VboUsage {

    STREAM_DRAW(BufferUsage.STREAM_DRAW),
    STREAM_READ(BufferUsage.STREAM_READ),
    STREAM_COPY(BufferUsage.STREAM_COPY),

    STATIC_DRAW(BufferUsage.STATIC_DRAW),
    STATIC_READ(BufferUsage.STATIC_READ),
    STATIC_COPY(BufferUsage.STATIC_COPY),

    DYNAMIC_DRAW(BufferUsage.DYNAMIC_DRAW),
    DYNAMIC_READ(BufferUsage.DYNAMIC_READ),
    DYNAMIC_COPY(BufferUsage.DYNAMIC_COPY);

    private final BufferUsage value;

    VboUsage(BufferUsage value) {
        this.value = value;
    }

    public BufferUsage get() {
        return this.value;
    }
}

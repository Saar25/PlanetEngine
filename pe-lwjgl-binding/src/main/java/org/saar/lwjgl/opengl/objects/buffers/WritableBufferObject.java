package org.saar.lwjgl.opengl.objects.buffers;

import java.nio.ByteBuffer;

public interface WritableBufferObject {

    /**
     * Allocate memory for the vbo
     *
     * @param capacity the bytes to allocate
     */
    void allocate(long capacity);

    /**
     * Store memory in the vbo
     *
     * @param offset the offset of the vbo
     * @param buffer the buffer to store in the vbo
     */
    void store(long offset, ByteBuffer buffer);
}

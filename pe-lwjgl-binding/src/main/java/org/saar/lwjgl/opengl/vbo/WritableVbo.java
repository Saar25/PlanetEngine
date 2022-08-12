package org.saar.lwjgl.opengl.vbo;

import java.nio.ByteBuffer;

public interface WritableVbo extends ReadOnlyVbo {

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

    /**
     * Map the vbo
     *
     * @param access the buffer access
     * @return the mapped byte buffer
     */
    ByteBuffer map(VboAccess access);

    /**
     * Unmap the vbo
     */
    void unmap();
}

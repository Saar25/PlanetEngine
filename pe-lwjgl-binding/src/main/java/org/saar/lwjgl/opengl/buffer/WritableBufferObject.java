package org.saar.lwjgl.opengl.buffer;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface WritableBufferObject extends ReadonlyBufferObject {

    /**
     * Allocate data to the buffer object
     *
     * @param target   the buffer target
     * @param capacity the capacity in bytes
     * @param usage    the buffer usage
     */
    void allocate(BufferTarget target, long capacity, BufferUsage usage);

    /**
     * Store int data to the buffer object
     *
     * @param target the buffer target
     * @param offset the data offset
     * @param buffer the data
     */
    void store(BufferTarget target, long offset, int[] buffer);

    /**
     * Store float data to the buffer object
     *
     * @param target the buffer target
     * @param offset the data offset
     * @param buffer the data
     */
    void store(BufferTarget target, long offset, float[] buffer);

    /**
     * Store byte data to the buffer object
     *
     * @param target the buffer target
     * @param offset the data offset
     * @param buffer the data
     */
    void store(BufferTarget target, long offset, ByteBuffer buffer);

    /**
     * Store int data to the buffer object
     *
     * @param target the buffer target
     * @param offset the data offset
     * @param buffer the data
     */
    void store(BufferTarget target, long offset, IntBuffer buffer);

    /**
     * Store float data to the buffer object
     *
     * @param target the buffer target
     * @param offset the data offset
     * @param buffer the data
     */
    void store(BufferTarget target, long offset, FloatBuffer buffer);

    /**
     * Map the buffer object
     *
     * @param target the buffer target
     * @param access the buffer access
     * @return the mapped byte buffer
     */
    ByteBuffer map(BufferTarget target, BufferAccess access);

    /**
     * Unmap the buffer object
     *
     * @param target the buffer target
     */
    void unmap(BufferTarget target);

}

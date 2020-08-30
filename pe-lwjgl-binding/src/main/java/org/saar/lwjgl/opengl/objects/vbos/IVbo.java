package org.saar.lwjgl.opengl.objects.vbos;

import org.saar.lwjgl.opengl.constants.VboAccess;

import java.nio.ByteBuffer;

public interface IVbo extends ReadOnlyVbo {

    /**
     * Allocate memory for the vbo
     *
     * @param size the bytes to allocate
     */
    void allocateByte(long size);

    /**
     * Store memory in the vbo
     *
     * @param offset the offset of the vbo
     * @param buffer the buffer to store in the vbo
     */
    void storeData(long offset, ByteBuffer buffer);

    /**
     * Map the vbo
     *
     * @param access the access type
     * @return the buffer to map
     */
    ByteBuffer map(VboAccess access);


    /**
     * Unmap the vbo
     */
    void unmap();

    /**
     * Delete the vbo
     */
    void delete();

}

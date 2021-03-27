package org.saar.lwjgl.opengl.objects.vbos;

import org.saar.lwjgl.opengl.objects.buffers.WritableBufferObject;

import java.nio.ByteBuffer;

public interface IVbo extends ReadOnlyVbo, WritableBufferObject {

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

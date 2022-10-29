package org.saar.lwjgl.opengl.buffer;

public interface ReadonlyBufferObject {

    /**
     * Returns the capacity allocated to the buffer object in bytes
     *
     * @return the capacity allocated to the buffer object in bytes
     */
    long getCapacity();

    /**
     * Bind the buffer object to the given target
     *
     * @param target the buffer target
     */
    void bind(BufferTarget target);

    /**
     * Unbind the buffer object from the given target
     *
     * @param target the buffer target
     */
    void unbind(BufferTarget target);

}

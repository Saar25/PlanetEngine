package org.saar.lwjgl.opengl.vbo;

public interface ReadOnlyVbo {

    /**
     * Returns the capacity allocated to the buffer object in bytes
     *
     * @return the capacity allocated to the buffer object in bytes
     */
    long getCapacity();

    /**
     * Bind the vbo
     */
    void bind();

    /**
     * Unbind the vbo
     */
    void unbind();

}

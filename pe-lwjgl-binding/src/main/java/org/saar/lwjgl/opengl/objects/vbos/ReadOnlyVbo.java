package org.saar.lwjgl.opengl.objects.vbos;

public interface ReadOnlyVbo {

    /**
     * Return the size of the vbo in bytes
     *
     * @return the size of the vbo int bytes
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

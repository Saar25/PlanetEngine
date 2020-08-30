package org.saar.lwjgl.opengl.objects.vbos;

public interface IVbo {

    /**
     * Return the size of the vbo in bytes
     *
     * @return the size of the vbo int bytes
     */
    long getSize();

    /**
     * Bind the vbo
     */
    void bind();

    /**
     * Unbind the vbo
     */
    void unbind();

    /**
     * Delete the vbo
     */
    void delete();

}

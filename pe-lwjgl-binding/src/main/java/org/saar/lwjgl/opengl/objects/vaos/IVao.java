package org.saar.lwjgl.opengl.objects.vaos;

public interface IVao extends ReadOnlyVao {

    /**
     * Enables the vao attributes
     */
    void enableAttributes();

    /**
     * Disables the vao attributes
     */
    void disableAttributes();

    /**
     * Delete this vao and its related buffers
     */
    void delete();

}

package org.saar.lwjgl.opengl.vao;

public interface ReadOnlyVao {

    /**
     * Enables the vao attributes
     */
    void enableAttributes();

    /**
     * Disables the vao attributes
     */
    void disableAttributes();

    /**
     * Bind the vao
     */
    void bind();

    /**
     * Unbind the vao
     */
    void unbind();

}

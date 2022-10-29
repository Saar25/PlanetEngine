package org.saar.lwjgl.opengl.fbo;

public interface IFbo extends ReadOnlyFbo, ReadableFbo, DrawableFbo, ModifiableFbo {

    /**
     * Sets the size of the fbo
     * *NOTE* you must resize the attachments too
     *
     * @param width  the width
     * @param height the height
     */
    void resize(int width, int height);

    /**
     * Delete the fbo
     */
    void delete();

}

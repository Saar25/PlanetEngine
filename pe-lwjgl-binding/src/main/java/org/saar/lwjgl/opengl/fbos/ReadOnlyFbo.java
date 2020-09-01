package org.saar.lwjgl.opengl.fbos;

import org.saar.lwjgl.opengl.fbos.exceptions.FrameBufferException;

public interface ReadOnlyFbo {

    /**
     * Returns the width of the fbo
     *
     * @return the width
     */
    int getWidth();

    /**
     * Returns the height of the fbo
     *
     * @return the height
     */
    int getHeight();

    /**
     * Bind the fbo
     */
    void bind();

    /**
     * Unbind the fbo
     */
    void unbind();

    /**
     * Ensure that the fbo status is good
     */
    void ensureStatus() throws FrameBufferException;
}

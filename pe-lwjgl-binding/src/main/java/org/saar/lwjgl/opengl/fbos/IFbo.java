package org.saar.lwjgl.opengl.fbos;

import org.saar.lwjgl.opengl.fbos.exceptions.FrameBufferException;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.utils.GlBuffer;

public interface IFbo extends ReadOnlyFbo, ReadableFbo, DrawableFbo {

    /**
     * Blit the fbo to the given fbo
     */
    void blitFbo(DrawableFbo fbo);

    void blitFbo(DrawableFbo fbo, MagFilterParameter filter, GlBuffer... buffers);

    /**
     * Sets the size of the fbo
     * WARNING: you must resize the attachments too
     *
     * @param width  the width
     * @param height the height
     */
    void resize(int width, int height);

    /**
     * Delete the fbo
     */
    void delete();

    /**
     * Ensure that the fbo status is good
     */
    void ensureStatus() throws FrameBufferException;

}

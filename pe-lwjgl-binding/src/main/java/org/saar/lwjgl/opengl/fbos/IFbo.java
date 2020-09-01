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
     * Delete the fbo
     */
    void delete();

    /**
     * Ensure that the fbo status is good
     */
    void ensureStatus() throws FrameBufferException;

}

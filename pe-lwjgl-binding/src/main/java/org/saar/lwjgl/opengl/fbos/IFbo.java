package org.saar.lwjgl.opengl.fbos;

import org.saar.lwjgl.opengl.utils.GlBuffer;

public interface IFbo extends ReadOnlyFbo, ReadableFbo, DrawableFbo {

    /**
     * Blit the fbo into the bound read fbo
     */
    void blitFramebuffer(int x1, int y1, int w1, int h1, int x2, int y2, int w2,
                         int h2, FboBlitFilter filter, GlBuffer... buffers);

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

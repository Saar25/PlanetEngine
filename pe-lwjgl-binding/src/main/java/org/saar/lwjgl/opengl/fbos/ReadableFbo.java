package org.saar.lwjgl.opengl.fbos;

import org.saar.lwjgl.opengl.utils.GlBuffer;

public interface ReadableFbo extends ReadOnlyFbo {

    /**
     * Set as read fbo
     */
    void bindAsRead();

    /**
     * Blit the fbo into the bound read fbo
     */
    void blitFramebuffer(int x1, int y1, int w1, int h1, int x2, int y2, int w2,
                         int h2, FboBlitFilter filter, GlBuffer... buffers);
}

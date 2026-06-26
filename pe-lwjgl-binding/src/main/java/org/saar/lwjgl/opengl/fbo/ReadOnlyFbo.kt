package org.saar.lwjgl.opengl.fbo;

import org.saar.lwjgl.opengl.fbo.exceptions.FrameBufferException;
import org.saar.lwjgl.opengl.utils.GlBuffer;

public interface ReadOnlyFbo {

    /**
     * Bind the fbo
     */
    void bind();

    /**
     * Unbind the fbo
     */
    void unbind();

    /**
     * Set as read fbo
     */
    void bindAsRead();

    /**
     * Set as read fbo
     */
    void bindAsDraw();

    /**
     * Blit the fbo into the bound read fbo
     */
    void blitFramebuffer(int x1, int y1, int w1, int h1, int x2, int y2, int w2,
                         int h2, FboBlitFilter filter, GlBuffer[] buffers);

    /**
     * Ensure that the fbo status is good
     */
    void ensureStatus() throws FrameBufferException;
}

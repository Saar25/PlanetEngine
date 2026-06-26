package org.saar.lwjgl.opengl.fbo

import org.saar.lwjgl.opengl.fbo.exceptions.FrameBufferException
import org.saar.lwjgl.opengl.utils.GlBuffer

interface ReadOnlyFbo {
    /**
     * Bind the fbo
     */
    fun bind()

    /**
     * Unbind the fbo
     */
    fun unbind()

    /**
     * Set as read fbo
     */
    fun bindAsRead()

    /**
     * Set as read fbo
     */
    fun bindAsDraw()

    /**
     * Blit the fbo into the bound read fbo
     */
    fun blitFramebuffer(
        x1: Int, y1: Int, w1: Int, h1: Int, x2: Int, y2: Int, w2: Int,
        h2: Int, filter: FboBlitFilter, buffers: Array<out GlBuffer>
    )

    /**
     * Ensure that the fbo status is good
     */
    @Throws(FrameBufferException::class)
    fun ensureStatus()
}

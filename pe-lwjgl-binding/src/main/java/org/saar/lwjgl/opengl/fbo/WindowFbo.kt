package org.saar.lwjgl.opengl.fbo

import org.lwjgl.opengl.GL11
import org.saar.lwjgl.opengl.fbo.exceptions.FrameBufferException
import org.saar.lwjgl.opengl.utils.GlBuffer

object WindowFbo : ReadOnlyFbo {

    private val fbo: Fbo get() = Fbo.NULL

    override fun blitFramebuffer(
        x1: Int, y1: Int, w1: Int, h1: Int,
        x2: Int, y2: Int, w2: Int, h2: Int,
        filter: FboBlitFilter, vararg buffers: GlBuffer
    ) = this.fbo.blitFramebuffer(x1, y1, w1, h1, x2, y2, w2, h2, filter, *buffers)

    override fun bindAsRead() {
        this.fbo.bind(FboTarget.READ_FRAMEBUFFER)
        GL11.glReadBuffer(GL11.GL_NONE)
    }

    override fun bindAsDraw() {
        this.fbo.bind(FboTarget.DRAW_FRAMEBUFFER)
        GL11.glDrawBuffer(GL11.GL_BACK)
    }

    override fun bind() = this.fbo.bind(FboTarget.FRAMEBUFFER)

    override fun unbind() = this.fbo.unbind()

    @Throws(FrameBufferException::class)
    override fun ensureStatus() = this.fbo.ensureStatus()
}

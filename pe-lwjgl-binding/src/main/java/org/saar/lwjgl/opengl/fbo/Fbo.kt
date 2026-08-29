package org.saar.lwjgl.opengl.fbo

import org.lwjgl.opengl.GL30
import org.saar.lwjgl.opengl.fbo.BoundFbo.isBound
import org.saar.lwjgl.opengl.fbo.BoundFbo.set
import org.saar.lwjgl.opengl.fbo.FboStatus.ensureStatus
import org.saar.lwjgl.opengl.fbo.attachment.IAttachment
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex
import org.saar.lwjgl.opengl.fbo.exceptions.FrameBufferException
import org.saar.lwjgl.opengl.fbo.rendertarget.DrawRenderTarget
import org.saar.lwjgl.opengl.fbo.rendertarget.ReadRenderTarget
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlConfigs

class Fbo private constructor(private val id: Int) : IFbo {

    companion object {
        val NULL: Fbo = Fbo(0)

        @JvmStatic
        fun create(): Fbo {
            val id = GL30.glGenFramebuffers()
            return Fbo(id)
        }
    }

    override fun addAttachment(index: AttachmentIndex, attachment: IAttachment) {
        bind()
        attachment.attach(this.id, index)
    }

    override fun setReadTarget(target: ReadRenderTarget) {
        bind()
        target.setAsRead()
    }

    override fun setDrawTarget(target: DrawRenderTarget) {
        bind()
        target.setAsDraw()
    }

    fun blitFramebuffer(w: Int, h: Int, filter: FboBlitFilter, vararg buffers: GlBuffer) {
        blitFramebuffer(0, 0, w, h, 0, 0, w, h, filter, *buffers)
    }

    fun blitFramebuffer(w1: Int, h1: Int, w2: Int, h2: Int, filter: FboBlitFilter, vararg buffers: GlBuffer) {
        blitFramebuffer(0, 0, w1, h1, 0, 0, w2, h2, filter, *buffers)
    }

    override fun blitFramebuffer(
        x1: Int, y1: Int, w1: Int, h1: Int, x2: Int, y2: Int, w2: Int,
        h2: Int, filter: FboBlitFilter, vararg buffers: GlBuffer
    ) {
        bindAsRead()
        GL30.glBlitFramebuffer(x1, y1, w1, h1, x2, y2, w2, h2, GlBuffer.getValue(*buffers), filter.get())
    }

    override fun bindAsRead() = bind(FboTarget.READ_FRAMEBUFFER)

    override fun bindAsDraw() = bind(FboTarget.DRAW_FRAMEBUFFER)

    override fun bind() = bind(FboTarget.FRAMEBUFFER)

    override fun unbind() = unbind(FboTarget.FRAMEBUFFER)

    override fun delete() = GL30.glDeleteFramebuffers(id)

    @Throws(FrameBufferException::class)
    override fun ensureStatus() {
        bind()
        val status = GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER)
        ensureStatus(status)
    }

    fun bind(target: FboTarget) {
        if (!GlConfigs.CACHE_STATE || isBound(target, this.id)) {
            bind0(target)
        }
    }

    fun unbind(target: FboTarget) = NULL.bind(target)

    private fun bind0(target: FboTarget) {
        GL30.glBindFramebuffer(target.get(), this.id)
        set(target, this.id)
    }
}

package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.FboBlitFilter
import org.saar.lwjgl.opengl.fbo.IFbo
import org.saar.lwjgl.opengl.fbo.attachment.IAttachment
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex
import org.saar.lwjgl.opengl.utils.GlBuffer

abstract class ScreenBase : Screen {

    override val width get() = this.fbo.width

    override val height get() = this.fbo.height

    override fun copyTo(other: Screen, filter: FboBlitFilter, vararg buffers: GlBuffer) {
        setAsRead()
        other.setAsDraw()
        this.fbo.blitFramebuffer(
            0, 0, this.width, this.height,
            0, 0, other.width, other.height,
            filter, buffers)
    }

    override fun setAsDraw() = this.fbo.bindAsDraw()

    override fun setAsRead() = this.fbo.bindAsRead()

    fun resize(width: Int, height: Int) {
        this.fbo.bind()
        this.fbo.resize(width, height)
        this.attachments.forEach { (index, attachment) -> attachment.init(this.fbo, index) }
    }

    fun delete() {
        this.fbo.delete()
        this.attachments.values.forEach(IAttachment::delete)
    }

    protected abstract val fbo: IFbo

    protected abstract val attachments: Map<AttachmentIndex, IAttachment>
}

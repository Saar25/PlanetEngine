package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.IFbo
import org.saar.lwjgl.opengl.fbo.attachment.IAttachment
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex
import org.saar.lwjgl.opengl.fbo.rendertarget.DrawRenderTargetComposite
import org.saar.lwjgl.opengl.fbo.rendertarget.IndexRenderTarget

class MutableScreen(override val fbo: IFbo) : ScreenBase(), OffScreen {

    private val attachments = mutableMapOf<AttachmentIndex, IAttachment>()

    override fun resize(width: Int, height: Int) {
        this.fbo.bind()
        this.fbo.resize(width, height)
        this.attachments.forEach { (index, attachment) -> attachment.init(this.fbo, index) }
    }

    override fun delete() {
        this.fbo.delete()
        this.attachments.values.forEach(IAttachment::delete)
    }

    fun addAttachment(index: AttachmentIndex, attachment: IAttachment) {
        this.attachments[index]?.delete()
        this.fbo.addAttachment(index, attachment)
        this.attachments[index] = attachment
    }

    fun removeAttachment(index: AttachmentIndex) {
        this.attachments.remove(index)?.delete()
    }

    fun setReadImage(index: ColorAttachmentIndex) {
        val target = IndexRenderTarget(index)
        this.fbo.setReadTarget(target)
    }

    fun setDrawImages(indices: Iterable<ColorAttachmentIndex>) {
        val targets = indices.map(::IndexRenderTarget)
        val target = DrawRenderTargetComposite(targets)
        this.fbo.setDrawTarget(target)
    }
}

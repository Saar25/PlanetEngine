package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.IFbo
import org.saar.lwjgl.opengl.fbo.attachment.IAttachment
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex
import org.saar.lwjgl.opengl.fbo.rendertarget.DrawRenderTargetComposite
import org.saar.lwjgl.opengl.fbo.rendertarget.IndexRenderTarget

class SimpleScreen(override val fbo: IFbo) : ScreenBase(), OffScreen {

    override val attachments = mutableMapOf<AttachmentIndex, IAttachment>()

    fun addAttachment(index: AttachmentIndex, attachment: IAttachment) {
        this.attachments[index]?.delete()
        this.fbo.addAttachment(index, attachment)
        this.attachments[index] = attachment
    }

    fun setDrawImages(vararg indices: ColorAttachmentIndex) {
        val targets = indices.map(::IndexRenderTarget)
        val target = DrawRenderTargetComposite(targets)
        this.fbo.setDrawTarget(target)
    }

    fun setReadImages(index: ColorAttachmentIndex) {
        val target = IndexRenderTarget(index)
        this.fbo.setReadTarget(target)
    }
}

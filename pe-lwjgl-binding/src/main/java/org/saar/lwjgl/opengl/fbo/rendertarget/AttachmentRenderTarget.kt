package org.saar.lwjgl.opengl.fbo.rendertarget

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.saar.lwjgl.opengl.fbo.attachment.Attachment
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex

class AttachmentRenderTarget(private val attachment: Attachment) : SingleRenderTarget, RenderTarget {

    override val index: AttachmentIndex get() = this.attachment.index

    override fun setAsRead() = GL11.glReadBuffer(this.index.value)

    override fun setAsDraw() = GL20.glDrawBuffer(this.index.value)
}
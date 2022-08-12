package org.saar.lwjgl.opengl.fbo.rendertarget

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex

class IndexRenderTarget(override val index: AttachmentIndex) : SingleRenderTarget, RenderTarget {

    override fun setAsRead() = GL11.glReadBuffer(this.index.value)

    override fun setAsDraw() = GL20.glDrawBuffer(this.index.value)
}
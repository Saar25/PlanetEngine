package org.saar.lwjgl.opengl.fbo.attachment.index

import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType

object DepthStencilAttachmentIndex : AttachmentIndex {

    override val value = AttachmentType.DEPTH_STENCIL.get()

    override val type = AttachmentType.DEPTH_STENCIL

}
package org.saar.lwjgl.opengl.fbo.attachment.index

import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType

object StencilAttachmentIndex : AttachmentIndex {

    override val value = AttachmentType.STENCIL.get()

    override val type = AttachmentType.STENCIL

}
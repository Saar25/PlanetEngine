package org.saar.lwjgl.opengl.fbo.attachment.index

import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType

object DepthAttachmentIndex : AttachmentIndex {

    override val value = AttachmentType.DEPTH.get()

    override val type = AttachmentType.DEPTH

}
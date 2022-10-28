package org.saar.lwjgl.opengl.fbo.attachment.index

import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType

class BasicAttachmentIndex(override val type: AttachmentType) : AttachmentIndex {

    override val value = this.type.get()
}
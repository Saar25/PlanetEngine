package org.saar.lwjgl.opengl.fbos.attachment.index

import org.saar.lwjgl.opengl.fbos.attachment.AttachmentType

class BasicAttachmentIndex(override val type: AttachmentType) : AttachmentIndex {

    override val value = this.type.get()
}
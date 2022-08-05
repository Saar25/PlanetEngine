package org.saar.lwjgl.opengl.fbos.attachment.index

import org.saar.lwjgl.opengl.fbos.attachment.AttachmentType

class ColourAttachmentIndex(index: Int) : AttachmentIndex {

    override val value = AttachmentType.COLOUR.get() + index

    override val type = AttachmentType.COLOUR

}
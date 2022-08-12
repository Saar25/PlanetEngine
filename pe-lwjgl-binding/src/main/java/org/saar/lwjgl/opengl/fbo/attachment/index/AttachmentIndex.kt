package org.saar.lwjgl.opengl.fbo.attachment.index

import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType

sealed interface AttachmentIndex {
    val type: AttachmentType
    val value: Int
}
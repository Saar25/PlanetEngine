package org.saar.lwjgl.opengl.fbos.attachment.index

import org.saar.lwjgl.opengl.fbos.attachment.AttachmentType

sealed interface AttachmentIndex {
    val type: AttachmentType
    val value: Int
}
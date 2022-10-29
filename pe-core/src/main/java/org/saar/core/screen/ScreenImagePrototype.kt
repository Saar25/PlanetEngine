package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex

data class ScreenImagePrototype(
    val index: AttachmentIndex,
    val buffer: AttachmentBuffer,
    val draw: Boolean = true,
    val read: Boolean = false,
)
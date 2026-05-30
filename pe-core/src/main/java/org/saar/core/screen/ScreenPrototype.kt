package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex

interface ScreenPrototype {
    val colorBuffers: Collection<AttachmentBuffer> get() = emptyList()
    val depthBuffer: AttachmentBuffer? get() = null
    val stencilBuffer: AttachmentBuffer? get() = null
    val depthStencilBuffer: AttachmentBuffer? get() = null

    val readIndex: ColourAttachmentIndex? get() = null
}

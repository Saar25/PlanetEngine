package org.saar.example.screen

import org.saar.core.screen.ScreenPrototype
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex

class MyScreenPrototype : ScreenPrototype {
    override val colorBuffers = listOf(
        RenderBufferAttachmentBuffer(InternalFormat.RGBA8)
    )

    override val depthBuffer = RenderBufferAttachmentBuffer(InternalFormat.DEPTH24)

    override val readIndex = ColourAttachmentIndex(0)
}

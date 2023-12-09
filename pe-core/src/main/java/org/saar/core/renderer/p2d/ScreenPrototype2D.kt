package org.saar.core.renderer.p2d

import org.saar.core.screen.ScreenPrototype
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class ScreenPrototype2D : ScreenPrototype {

    private val colourTexture = MutableTexture2D.create()

    override val colorBuffers = listOf(
        TextureAttachmentBuffer(this.colourTexture, InternalFormat.RGB16F)
    )

    override val readIndex = ColorAttachmentIndex.at(0)

    val buffers = object : RenderingBuffers2D {
        override val albedo = colourTexture
    }
}
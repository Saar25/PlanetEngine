package org.saar.core.renderer.forward

import org.saar.core.screen.ScreenPrototype
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class ForwardScreenPrototype : ScreenPrototype {

    private val colourTexture = MutableTexture2D.create()

    private val depthTexture = MutableTexture2D.create()

    override val colorBuffers = listOf(
        TextureAttachmentBuffer(this.colourTexture, InternalFormat.RGB16)
    )

    override val depthBuffer = TextureAttachmentBuffer(this.depthTexture, InternalFormat.DEPTH24)

    override val readIndex = ColorAttachmentIndex.at(0)

    val buffers = object : ForwardRenderingBuffers {
        override val albedo = colourTexture
        override val depth = depthTexture
    }
}
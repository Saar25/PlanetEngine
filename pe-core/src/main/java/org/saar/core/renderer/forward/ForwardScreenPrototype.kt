package org.saar.core.renderer.forward

import org.saar.core.renderer.RenderingPathScreenPrototype
import org.saar.core.screen.ScreenImagePrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.BasicAttachmentIndex
import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class ForwardScreenPrototype : RenderingPathScreenPrototype<ForwardRenderingBuffers> {

    private val colourTexture = MutableTexture2D.create()

    private val depthTexture = MutableTexture2D.create()

    @ScreenImageProperty
    private val colourImage = ScreenImagePrototype(ColourAttachmentIndex(0),
        TextureAttachmentBuffer(this.colourTexture, InternalFormat.RGB16), read = true)

    @ScreenImageProperty
    private val depthImage = ScreenImagePrototype(BasicAttachmentIndex(AttachmentType.DEPTH),
        TextureAttachmentBuffer(this.depthTexture, InternalFormat.DEPTH24))

    override val buffers = object : ForwardRenderingBuffers {
        override val albedo = colourTexture
        override val depth = depthTexture
    }
}
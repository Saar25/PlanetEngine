package org.saar.core.renderer.forward

import org.saar.core.renderer.RenderingPathScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ScreenImage
import org.saar.core.screen.image.SimpleScreenImage
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.ColourAttachment
import org.saar.lwjgl.opengl.fbo.attachment.DepthAttachment
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class ForwardScreenPrototype : RenderingPathScreenPrototype<ForwardRenderingBuffers> {

    private val colourTexture = MutableTexture2D.create()

    private val depthTexture = MutableTexture2D.create()

    @ScreenImageProperty
    private val colourImage: ScreenImage = SimpleScreenImage(ColourAttachment(0,
        TextureAttachmentBuffer(this.colourTexture, InternalFormat.RGB16),
        SimpleAllocationStrategy()))

    @ScreenImageProperty
    private val depthImage: ScreenImage = SimpleScreenImage(DepthAttachment(
        TextureAttachmentBuffer(this.depthTexture, InternalFormat.RGB16),
        SimpleAllocationStrategy()))

    override val buffers = object : ForwardRenderingBuffers {
        override val albedo = colourTexture
        override val depth = depthTexture
    }
}
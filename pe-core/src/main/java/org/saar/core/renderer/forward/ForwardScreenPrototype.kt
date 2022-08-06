package org.saar.core.renderer.forward

import org.saar.core.renderer.RenderingPathScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ColourScreenImage
import org.saar.core.screen.image.DepthScreenImage
import org.saar.core.screen.image.ScreenImage
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment
import org.saar.lwjgl.opengl.fbos.attachment.allocation.SimpleAllocationStrategy
import org.saar.lwjgl.opengl.fbos.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class ForwardScreenPrototype : RenderingPathScreenPrototype<ForwardRenderingBuffers> {

    private val colourTexture = MutableTexture2D.create()

    private val depthTexture = MutableTexture2D.create()

    @ScreenImageProperty
    private val colourImage: ScreenImage = ColourScreenImage(ColourAttachment(0,
        TextureAttachmentBuffer(this.colourTexture, InternalFormat.RGB16),
        SimpleAllocationStrategy()))

    @ScreenImageProperty
    private val depthImage: ScreenImage = DepthScreenImage(DepthAttachment(
        TextureAttachmentBuffer(this.depthTexture, InternalFormat.RGB16),
        SimpleAllocationStrategy()))

    override val buffers = object : ForwardRenderingBuffers {
        override val albedo = colourTexture
        override val depth = depthTexture
    }
}
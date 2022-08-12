package org.saar.core.renderer.deferred

import org.saar.core.renderer.RenderingPathScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ScreenImage
import org.saar.core.screen.image.SimpleScreenImage
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.ColourAttachment
import org.saar.lwjgl.opengl.fbo.attachment.DepthStencilAttachment
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class DeferredScreenPrototype : RenderingPathScreenPrototype<DeferredRenderingBuffers> {

    private val colourTexture = MutableTexture2D.create()

    private val normalSpecularTexture = MutableTexture2D.create()

    private val depthTexture = MutableTexture2D.create()

    @ScreenImageProperty(draw = true, read = true)
    private val colourImage: ScreenImage = SimpleScreenImage(ColourAttachment(0,
        TextureAttachmentBuffer(this.colourTexture, InternalFormat.RGBA16F),
        SimpleAllocationStrategy()))

    @ScreenImageProperty(draw = true)
    private val normalSpecularImage: ScreenImage = SimpleScreenImage(ColourAttachment(1,
        TextureAttachmentBuffer(this.normalSpecularTexture, InternalFormat.RGBA16F),
        SimpleAllocationStrategy()))

    @ScreenImageProperty
    private val depthImage: ScreenImage = SimpleScreenImage(DepthStencilAttachment(
        TextureAttachmentBuffer(this.depthTexture, InternalFormat.DEPTH24_STENCIL8),
        SimpleAllocationStrategy()))

    override val buffers = object : DeferredRenderingBuffers {
        override val albedo = colourTexture
        override val normalSpecular = normalSpecularTexture
        override val depth = depthTexture
    }
}
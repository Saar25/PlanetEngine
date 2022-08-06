package org.saar.core.renderer.deferred

import org.saar.core.renderer.RenderingPathScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ColourScreenImage
import org.saar.core.screen.image.DepthStencilScreenImage
import org.saar.core.screen.image.ScreenImage
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.fbos.attachment.DepthStencilAttachment
import org.saar.lwjgl.opengl.fbos.attachment.allocation.SimpleTextureAllocation
import org.saar.lwjgl.opengl.fbos.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class DeferredScreenPrototype : RenderingPathScreenPrototype<DeferredRenderingBuffers> {

    private val colourTexture = MutableTexture2D.create()

    private val normalSpecularTexture = MutableTexture2D.create()

    private val depthTexture = MutableTexture2D.create()

    @ScreenImageProperty(draw = true, read = true)
    private val colourImage: ScreenImage = ColourScreenImage(ColourAttachment(0, TextureAttachmentBuffer(
        this.colourTexture, SimpleTextureAllocation(InternalFormat.RGBA16F)
    )))

    @ScreenImageProperty(draw = true)
    private val normalSpecularImage: ScreenImage = ColourScreenImage(ColourAttachment(1, TextureAttachmentBuffer(
        this.normalSpecularTexture, SimpleTextureAllocation(InternalFormat.RGBA16F)
    )))

    @ScreenImageProperty
    private val depthImage: ScreenImage = DepthStencilScreenImage(DepthStencilAttachment(TextureAttachmentBuffer(
        this.depthTexture, SimpleTextureAllocation(InternalFormat.DEPTH24_STENCIL8)
    )))

    override val buffers = object : DeferredRenderingBuffers {
        override val albedo = colourTexture
        override val normalSpecular = normalSpecularTexture
        override val depth = depthTexture
    }
}
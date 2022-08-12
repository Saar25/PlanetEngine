package org.saar.core.renderer.p2d

import org.saar.core.renderer.RenderingPathScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ScreenImage
import org.saar.core.screen.image.SimpleScreenImage
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.ColourAttachment
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class ScreenPrototype2D : RenderingPathScreenPrototype<RenderingBuffers2D> {

    private val colourTexture = MutableTexture2D.create()

    @ScreenImageProperty(draw = true, read = true)
    private val colourImage: ScreenImage = SimpleScreenImage(ColourAttachment(0,
        TextureAttachmentBuffer(this.colourTexture, InternalFormat.RGB16F),
        SimpleAllocationStrategy()))

    override val buffers = object : RenderingBuffers2D {
        override val albedo = colourTexture
    }
}
package org.saar.core.renderer.p2d

import org.saar.core.renderer.RenderingPathScreenPrototype
import org.saar.core.screen.ScreenImagePrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class ScreenPrototype2D : RenderingPathScreenPrototype<RenderingBuffers2D> {

    private val colourTexture = MutableTexture2D.create()

    @ScreenImageProperty
    private val colourImage = ScreenImagePrototype(ColourAttachmentIndex(0),
        TextureAttachmentBuffer(this.colourTexture, InternalFormat.RGB16F), read = true)

    override val buffers = object : RenderingBuffers2D {
        override val albedo = colourTexture
    }
}
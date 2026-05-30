package org.saar.core.renderer.deferred

import org.saar.core.renderer.RenderingPathScreenPrototype
import org.saar.core.screen.ScreenImagePrototype
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex
import org.saar.lwjgl.opengl.fbo.attachment.index.DepthStencilAttachmentIndex
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class DeferredScreenPrototype : RenderingPathScreenPrototype<DeferredRenderingBuffers> {

    private val colourTexture = MutableTexture2D.create()

    private val normalSpecularTexture = MutableTexture2D.create()

    private val depthTexture = MutableTexture2D.create()

    override val screenImages = listOf(
        ScreenImagePrototype(
            ColourAttachmentIndex(0),
            TextureAttachmentBuffer(this.colourTexture, InternalFormat.RGBA16F),
        ),
        ScreenImagePrototype(
            ColourAttachmentIndex(1),
            TextureAttachmentBuffer(this.normalSpecularTexture, InternalFormat.RGBA16F)
        ),
        ScreenImagePrototype(
            DepthStencilAttachmentIndex,
            TextureAttachmentBuffer(this.depthTexture, InternalFormat.DEPTH24_STENCIL8)
        )
    )

    override val readIndex = ColourAttachmentIndex(0)

    override val buffers = object : DeferredRenderingBuffers {
        override val albedo = colourTexture
        override val normalSpecular = normalSpecularTexture
        override val depth = depthTexture
    }
}
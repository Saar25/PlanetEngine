package org.saar.core.renderer.shadow

import org.saar.core.renderer.RenderingPathScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ScreenImage
import org.saar.core.screen.image.SimpleScreenImage
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.DepthAttachment
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.parameter.*
import org.saar.lwjgl.opengl.texture.values.MagFilterValue
import org.saar.lwjgl.opengl.texture.values.MinFilterValue
import org.saar.lwjgl.opengl.texture.values.WrapValue

class ShadowsScreenPrototype : RenderingPathScreenPrototype<ShadowsBuffers> {

    private val depthTexture = MutableTexture2D.create().apply {
        applyParameters(arrayOf<TextureParameter>(
            TextureMinFilterParameter(MinFilterValue.LINEAR),
            TextureMagFilterParameter(MagFilterValue.LINEAR),
            TextureSWrapParameter(WrapValue.CLAMP_TO_EDGE),
            TextureTWrapParameter(WrapValue.CLAMP_TO_EDGE)
        ))
    }

    @ScreenImageProperty
    private val depthImage: ScreenImage = SimpleScreenImage(DepthAttachment(
        TextureAttachmentBuffer(this.depthTexture, InternalFormat.DEPTH24),
        SimpleAllocationStrategy()))

    override val buffers = object : ShadowsBuffers {
        override val depth = depthTexture
    }
}
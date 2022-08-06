package org.saar.core.renderer.shadow

import org.saar.core.renderer.RenderingPathScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.DepthScreenImage
import org.saar.core.screen.image.ScreenImage
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment
import org.saar.lwjgl.opengl.fbos.attachment.allocation.SimpleRenderBufferAllocation
import org.saar.lwjgl.opengl.fbos.attachment.buffer.RenderBufferAttachmentBuffer
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer
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
    private val depthImage: ScreenImage = DepthScreenImage(DepthAttachment(
        RenderBufferAttachmentBuffer(RenderBuffer.create(),
            SimpleRenderBufferAllocation(InternalFormat.DEPTH24))))

    override val buffers = object : ShadowsBuffers {
        override val depth = depthTexture
    }
}
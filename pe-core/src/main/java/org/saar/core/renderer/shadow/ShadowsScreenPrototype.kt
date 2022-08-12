package org.saar.core.renderer.shadow

import org.saar.core.renderer.RenderingPathScreenPrototype
import org.saar.core.screen.ScreenImagePrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.BasicAttachmentIndex
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
    private val depthImage = ScreenImagePrototype(BasicAttachmentIndex(AttachmentType.DEPTH),
        TextureAttachmentBuffer(this.depthTexture, InternalFormat.DEPTH24))

    override val buffers = object : ShadowsBuffers {
        override val depth = depthTexture
    }
}
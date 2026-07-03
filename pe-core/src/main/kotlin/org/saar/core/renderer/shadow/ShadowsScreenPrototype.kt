package org.saar.core.renderer.shadow

import org.saar.core.screen.ScreenPrototype
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.lwjgl.opengl.texture.parameter.TextureMagFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureMinFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureSWrapParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureTWrapParameter
import org.saar.lwjgl.opengl.texture.values.MagFilterValue
import org.saar.lwjgl.opengl.texture.values.MinFilterValue
import org.saar.lwjgl.opengl.texture.values.WrapValue

class ShadowsScreenPrototype : ScreenPrototype {

    private val _depthTexture = MutableTexture2D.create().apply {
        applyParameters(
            TextureMinFilterParameter(MinFilterValue.LINEAR),
            TextureMagFilterParameter(MagFilterValue.LINEAR),
            TextureSWrapParameter(WrapValue.CLAMP_TO_EDGE),
            TextureTWrapParameter(WrapValue.CLAMP_TO_EDGE)
        )
    }
    val depthTexture: ReadOnlyTexture2D get() = this._depthTexture

    override val depthBuffer = TextureAttachmentBuffer(this._depthTexture, InternalFormat.DEPTH24)
}
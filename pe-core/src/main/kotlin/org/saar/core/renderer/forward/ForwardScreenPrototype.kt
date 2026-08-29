package org.saar.core.renderer.forward

import org.saar.core.screen.ScreenPrototype
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class ForwardScreenPrototype : ScreenPrototype {

    private val _albedoTexture = MutableTexture2D.create()
    val albedoTexture: ReadOnlyTexture2D get() = this._albedoTexture

    private val _depthTexture = MutableTexture2D.create()
    val depthTexture: ReadOnlyTexture2D get() = this._depthTexture

    override val colorBuffers = listOf(
        TextureAttachmentBuffer(this._albedoTexture, InternalFormat.RGB16)
    )

    override val depthBuffer = TextureAttachmentBuffer(this._depthTexture, InternalFormat.DEPTH24)

    override val readIndex = ColorAttachmentIndex.at(0)
}
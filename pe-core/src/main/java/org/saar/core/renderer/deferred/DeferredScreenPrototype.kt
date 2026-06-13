package org.saar.core.renderer.deferred

import org.saar.core.screen.ScreenPrototype
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class DeferredScreenPrototype : ScreenPrototype {

    private val _albedoTexture = MutableTexture2D.create()
    val albedoTexture: ReadOnlyTexture2D get() = this._albedoTexture

    private val _normalSpecularTexture = MutableTexture2D.create()
    val normalSpecularTexture: ReadOnlyTexture2D get() = this._normalSpecularTexture

    private val _depthTexture = MutableTexture2D.create()
    val depthTexture: ReadOnlyTexture2D get() = this._depthTexture

    override val colorBuffers = listOf(
        TextureAttachmentBuffer(this._albedoTexture, InternalFormat.RGBA16F),
        TextureAttachmentBuffer(this._normalSpecularTexture, InternalFormat.RGBA16F),
    )

    override val depthStencilBuffer = TextureAttachmentBuffer(this._depthTexture, InternalFormat.DEPTH24_STENCIL8)

    override val readIndex = ColorAttachmentIndex.at(0)
}
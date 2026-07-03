package org.saar.core.renderer.p2d

import org.saar.core.screen.ScreenPrototype
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class ScreenPrototype2D : ScreenPrototype {

    private val _albedoTexture = MutableTexture2D.create()
    val albedoTexture: ReadOnlyTexture2D get() = this._albedoTexture

    override val colorBuffers = listOf(
        TextureAttachmentBuffer(this._albedoTexture, InternalFormat.RGB16F)
    )

    override val readIndex = ColorAttachmentIndex.at(0)
}
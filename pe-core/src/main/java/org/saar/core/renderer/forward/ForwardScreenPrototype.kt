package org.saar.core.renderer.forward

import org.saar.core.screen.ScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ColourScreenImage
import org.saar.core.screen.image.ScreenImage
import org.saar.lwjgl.opengl.constants.ColourFormatType
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.textures.Texture
import org.saar.lwjgl.opengl.textures.TextureTarget

class ForwardScreenPrototype : ScreenPrototype {

    private val colourTexture = Texture.create(TextureTarget.TEXTURE_2D)

    private val depthTexture = Texture.create(TextureTarget.TEXTURE_2D)

    @ScreenImageProperty
    private val colourImage: ScreenImage = ColourScreenImage(ColourAttachment.withTexture(
        0, colourTexture, ColourFormatType.RGB16, FormatType.RGB, DataType.U_BYTE))

    @ScreenImageProperty
    private val depthImage: ScreenImage = ColourScreenImage(ColourAttachment.withTexture(
        0, depthTexture, ColourFormatType.RGB16, FormatType.RGB, DataType.U_BYTE))

    fun asBuffers() = ForwardRenderingBuffers(this.colourTexture, this.depthTexture)
}
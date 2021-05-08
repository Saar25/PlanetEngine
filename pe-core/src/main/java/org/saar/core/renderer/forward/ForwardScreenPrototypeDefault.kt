package org.saar.core.renderer.forward

import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ColourScreenImage
import org.saar.core.screen.image.ScreenImage
import org.saar.lwjgl.opengl.constants.ColourFormatType
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.textures.Texture

class ForwardScreenPrototypeDefault : ForwardScreenPrototype {

    private val colourTexture = Texture.create()

    @ScreenImageProperty
    private val colourImage: ScreenImage = ColourScreenImage(ColourAttachment.withTexture(
        0, colourTexture, ColourFormatType.RGB16, FormatType.RGB, DataType.U_BYTE))

    override fun getColourTexture(): ReadOnlyTexture = this.colourTexture
}
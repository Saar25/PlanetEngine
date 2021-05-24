package org.saar.core.postprocessing

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

class PostProcessingScreenPrototype : ScreenPrototype {

    val colourTexture: Texture = Texture.create(TextureTarget.TEXTURE_2D)

    @ScreenImageProperty(draw = true, read = true)
    private val colourImage: ScreenImage = ColourScreenImage(
        ColourAttachment.withTexture(0, this.colourTexture,
            ColourFormatType.RGBA16, FormatType.RGBA, DataType.U_BYTE)
    )
}
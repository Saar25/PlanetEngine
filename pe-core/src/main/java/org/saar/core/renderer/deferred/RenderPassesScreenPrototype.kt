package org.saar.core.renderer.deferred

import org.saar.core.screen.ScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ColourScreenImage
import org.saar.core.screen.image.ScreenImage
import org.saar.lwjgl.opengl.constants.ColourFormatType
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.textures.Texture

class RenderPassesScreenPrototype : ScreenPrototype {

    val colourTexture: Texture = Texture.create()

    @ScreenImageProperty(draw = true, read = true)
    private val colourImage: ScreenImage = ColourScreenImage(
        ColourAttachment.withTexture(0, this.colourTexture,
            ColourFormatType.RGBA16, FormatType.RGBA, DataType.U_BYTE)
    )
}
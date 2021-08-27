package org.saar.core.renderer.renderpass

import org.saar.core.screen.ScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ColourScreenImage
import org.saar.core.screen.image.DepthStencilScreenImage
import org.saar.core.screen.image.ScreenImage
import org.saar.lwjgl.opengl.constants.ColourFormatType
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.DepthStencilFormatType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.fbos.attachment.DepthStencilAttachment
import org.saar.lwjgl.opengl.textures.Texture
import org.saar.lwjgl.opengl.textures.TextureTarget

class RenderPassScreenPrototype : ScreenPrototype {

    val colourTexture: Texture = Texture.create(TextureTarget.TEXTURE_2D)

    val depthTexture: Texture = Texture.create(TextureTarget.TEXTURE_2D)

    @ScreenImageProperty(draw = true, read = true)
    private val colourImage: ScreenImage = ColourScreenImage(
        ColourAttachment.withTexture(0, this.colourTexture,
            ColourFormatType.RGBA16, FormatType.RGBA, DataType.U_BYTE)
    )

    @ScreenImageProperty
    private val depthImage: ScreenImage = DepthStencilScreenImage(DepthStencilAttachment.withTexture(
        this.depthTexture, DepthStencilFormatType.DEPTH24_STENCIL8, DataType.U_INT_24_8))
}
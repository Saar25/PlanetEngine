package org.saar.core.renderer.deferred

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

class DeferredScreenPrototype : ScreenPrototype {

    private val colourTexture = Texture.create(TextureTarget.TEXTURE_2D)

    private val normalTexture = Texture.create(TextureTarget.TEXTURE_2D)

    private val specularTexture = Texture.create(TextureTarget.TEXTURE_2D)

    private val depthTexture = Texture.create(TextureTarget.TEXTURE_2D)

    @ScreenImageProperty(draw = true, read = true)
    private val colourImage: ScreenImage = ColourScreenImage(ColourAttachment.withTexture(
        0, this.colourTexture, ColourFormatType.RGB16, FormatType.RGB, DataType.U_BYTE))

    @ScreenImageProperty(draw = true)
    private val normalImage: ScreenImage = ColourScreenImage(ColourAttachment.withTexture(
        1, this.normalTexture, ColourFormatType.RGB16F, FormatType.RGB, DataType.U_BYTE))

    @ScreenImageProperty(draw = true)
    private val specularImage: ScreenImage = ColourScreenImage(ColourAttachment.withTexture(
        2, this.specularTexture, ColourFormatType.R16F, FormatType.RED, DataType.FLOAT))

    @ScreenImageProperty
    private val depthImage: ScreenImage = DepthStencilScreenImage(DepthStencilAttachment.withTexture(
        this.depthTexture, DepthStencilFormatType.DEPTH24_STENCIL8, DataType.U_INT_24_8))

    fun asBuffers() = DeferredRenderingBuffers(
        this.colourTexture, this.normalTexture,
        this.specularTexture, this.depthTexture)
}
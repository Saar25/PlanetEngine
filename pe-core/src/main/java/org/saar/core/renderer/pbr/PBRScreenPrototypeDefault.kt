package org.saar.core.renderer.pbr

import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ColourScreenImage
import org.saar.core.screen.image.DepthScreenImage
import org.saar.core.screen.image.ScreenImage
import org.saar.lwjgl.opengl.constants.ColourFormatType
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.DepthFormatType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.textures.Texture
import org.saar.lwjgl.opengl.textures.TextureTarget

class PBRScreenPrototypeDefault : PBRScreenPrototype {

    private val colourTexture = Texture.create(TextureTarget.TEXTURE_2D)

    private val normalTexture = Texture.create(TextureTarget.TEXTURE_2D)

    private val reflectivityTexture = Texture.create(TextureTarget.TEXTURE_2D)

    private val depthTexture = Texture.create(TextureTarget.TEXTURE_2D)

    @ScreenImageProperty(draw = true, read = true)
    private val colourImage: ScreenImage = ColourScreenImage(ColourAttachment.withTexture(
        0, this.colourTexture, ColourFormatType.RGB16, FormatType.RGB, DataType.U_BYTE))

    @ScreenImageProperty(draw = true)
    private val normalImage: ScreenImage = ColourScreenImage(ColourAttachment.withTexture(
        1, this.normalTexture, ColourFormatType.RGB16, FormatType.RGB, DataType.U_BYTE))

    @ScreenImageProperty(draw = true)
    private val reflectivityImage: ScreenImage = ColourScreenImage(ColourAttachment.withTexture(
        1, this.normalTexture, ColourFormatType.R16, FormatType.RED, DataType.U_BYTE))

    @ScreenImageProperty
    private val depthImage: ScreenImage = DepthScreenImage(DepthAttachment.withTexture(
        this.depthTexture, DepthFormatType.COMPONENT24, DataType.U_BYTE))

    override fun getColourTexture(): ReadOnlyTexture = this.colourTexture

    override fun getNormalTexture(): ReadOnlyTexture = this.normalTexture

    override fun getReflectivityTexture(): ReadOnlyTexture = this.reflectivityTexture

    override fun getDepthTexture(): ReadOnlyTexture = this.depthTexture
}
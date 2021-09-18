package org.saar.core.renderer.deferred

import org.saar.core.screen.ScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ColourScreenImage
import org.saar.core.screen.image.DepthStencilScreenImage
import org.saar.core.screen.image.ScreenImage
import org.saar.lwjgl.opengl.constants.ColourFormatType
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.DepthStencilFormatType
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.fbos.attachment.DepthStencilAttachment
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class DeferredScreenPrototype : ScreenPrototype {

    private val colourTexture = MutableTexture2D.create()

    private val normalSpecularTexture = MutableTexture2D.create()

    private val depthTexture = MutableTexture2D.create()

    @ScreenImageProperty(draw = true, read = true)
    private val colourImage: ScreenImage = ColourScreenImage(ColourAttachment
        .withTexture(0, this.colourTexture, ColourFormatType.RGBA16F))

    @ScreenImageProperty(draw = true)
    private val normalSpecularImage: ScreenImage = ColourScreenImage(ColourAttachment
        .withTexture(1, this.normalSpecularTexture, ColourFormatType.RGBA16F))

    @ScreenImageProperty
    private val depthImage: ScreenImage = DepthStencilScreenImage(DepthStencilAttachment.withTexture(
        this.depthTexture, DepthStencilFormatType.DEPTH24_STENCIL8, DataType.U_INT_24_8))

    fun asBuffers() = DeferredRenderingBuffers(this.colourTexture, this.normalSpecularTexture, this.depthTexture)
}
package org.saar.core.renderer.forward

import org.saar.core.screen.ScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ColourScreenImage
import org.saar.core.screen.image.ScreenImage
import org.saar.lwjgl.opengl.constants.ColourFormatType
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class ForwardScreenPrototype : ScreenPrototype {

    private val colourTexture = MutableTexture2D.create()

    private val depthTexture = MutableTexture2D.create()

    @ScreenImageProperty
    private val colourImage: ScreenImage = ColourScreenImage(ColourAttachment
        .withTexture(0, colourTexture, ColourFormatType.RGB16))

    @ScreenImageProperty
    private val depthImage: ScreenImage = ColourScreenImage(ColourAttachment
        .withTexture(0, depthTexture, ColourFormatType.RGB16))

    val buffers = object : ForwardRenderingBuffers {
        override val albedo = colourTexture
        override val depth = depthTexture
    }
}
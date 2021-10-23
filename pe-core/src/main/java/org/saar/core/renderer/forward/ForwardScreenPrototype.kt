package org.saar.core.renderer.forward

import org.saar.core.renderer.RenderingPathScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ColourScreenImage
import org.saar.core.screen.image.DepthScreenImage
import org.saar.core.screen.image.ScreenImage
import org.saar.lwjgl.opengl.constants.ColourFormatType
import org.saar.lwjgl.opengl.constants.DepthFormatType
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class ForwardScreenPrototype : RenderingPathScreenPrototype<ForwardRenderingBuffers> {

    private val colourTexture = MutableTexture2D.create()

    private val depthTexture = MutableTexture2D.create()

    @ScreenImageProperty(draw = true, read = true)
    private val colourImage: ScreenImage = ColourScreenImage(
        ColourAttachment.withTexture(0, colourTexture, ColourFormatType.RGB16))

    @ScreenImageProperty
    private val depthImage: ScreenImage = DepthScreenImage(
        DepthAttachment.withTexture(depthTexture, DepthFormatType.COMPONENT24))

    override val buffers = object : ForwardRenderingBuffers {
        override val albedo = colourTexture
        override val depth = depthTexture
    }
}
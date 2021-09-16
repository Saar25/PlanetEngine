package org.saar.core.renderer.shadow

import org.saar.core.screen.ScreenPrototype
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.DepthScreenImage
import org.saar.core.screen.image.ScreenImage
import org.saar.lwjgl.opengl.constants.DepthFormatType
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class ShadowsScreenPrototype : ScreenPrototype {

    val depthTexture = MutableTexture2D.create()

    @ScreenImageProperty
    private val depthImage: ScreenImage = DepthScreenImage(DepthAttachment
        .withTexture(depthTexture, DepthFormatType.COMPONENT24))
}
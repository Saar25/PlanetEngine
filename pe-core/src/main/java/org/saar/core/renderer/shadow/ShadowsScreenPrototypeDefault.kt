package org.saar.core.renderer.shadow

import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.DepthScreenImage
import org.saar.core.screen.image.ScreenImage
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.DepthFormatType
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment
import org.saar.lwjgl.opengl.textures.Texture
import org.saar.lwjgl.opengl.textures.TextureTarget

class ShadowsScreenPrototypeDefault : ShadowsScreenPrototype {
    private val depthTexture = Texture.create(TextureTarget.TEXTURE_2D)

    @ScreenImageProperty
    private val depthImage: ScreenImage = DepthScreenImage(DepthAttachment.withTexture(
        depthTexture, DepthFormatType.COMPONENT24, DataType.U_BYTE))

    override fun getDepthTexture(): Texture = this.depthTexture
}
package org.saar.core.renderer.deferred.shadow;

import org.saar.core.screen.ScreenPrototype;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.DepthScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.TextureTarget;

public class ShadowsScreenPrototype implements ScreenPrototype {

    private final Texture depthTexture = Texture.create(TextureTarget.TEXTURE_2D);

    @ScreenImageProperty
    private final ScreenImage depthImage = new DepthScreenImage(DepthAttachment.withTexture(
            this.depthTexture, DepthFormatType.COMPONENT24, DataType.U_BYTE));

    public Texture getDepthTexture() {
        return this.depthTexture;
    }
}

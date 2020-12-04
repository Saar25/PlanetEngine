package org.saar.example;

import org.saar.core.renderer.deferred.DeferredScreenPrototype;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.DepthScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.TextureTarget;

public class MyScreenPrototype implements DeferredScreenPrototype {

    private final Texture colourTexture = Texture.create(TextureTarget.TEXTURE_2D);

    @ScreenImageProperty(draw = true, read = true)
    private final ScreenImage colourImage = new ColourScreenImage(ColourAttachment.withTexture(
            0, this.colourTexture, ColourFormatType.RGB16, FormatType.RGB, DataType.U_BYTE));

    private final Texture normalTexture = Texture.create(TextureTarget.TEXTURE_2D);

    @ScreenImageProperty(draw = true)
    private final ScreenImage normalImage = new ColourScreenImage(ColourAttachment.withTexture(
            1, this.normalTexture, ColourFormatType.RGB16, FormatType.RGB, DataType.U_BYTE));

    private final Texture depthTexture = Texture.create(TextureTarget.TEXTURE_2D);

    @ScreenImageProperty
    private final ScreenImage depthImage = new DepthScreenImage(DepthAttachment.withTexture(
            this.depthTexture, DepthFormatType.COMPONENT24, DataType.U_BYTE));

    @Override
    public Texture getColourTexture() {
        return this.colourTexture;
    }

    @Override
    public Texture getNormalTexture() {
        return this.normalTexture;
    }

    @Override
    public Texture getDepthTexture() {
        return this.depthTexture;
    }
}
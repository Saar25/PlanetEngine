package org.saar.example.screen;

import org.saar.core.screen.ScreenPrototype;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ColourScreenImageBase;
import org.saar.core.screen.image.DepthScreenImageBase;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment;

public class MyScreenPrototype implements ScreenPrototype {

    @ScreenImageProperty
    private final ScreenImage colourImage = new ColourScreenImageBase(
            ColourAttachment.withRenderBuffer(0, InternalFormat.RGBA8));

    @ScreenImageProperty
    private final ScreenImage depthImage = new DepthScreenImageBase(
            DepthAttachment.withRenderBuffer(DepthFormatType.COMPONENT24));
}

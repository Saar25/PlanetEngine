package org.saar.example.screen;

import org.saar.core.screen.ScreenPrototype;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.DepthScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment;

public class MyScreenPrototype implements ScreenPrototype {

    @ScreenImageProperty
    private final ScreenImage colourImage = new ColourScreenImage(
            ColourAttachment.withRenderBuffer(0, ColourFormatType.RGBA8));

    @ScreenImageProperty
    private final ScreenImage depthImage = new DepthScreenImage(
            DepthAttachment.withRenderBuffer(DepthFormatType.COMPONENT24));
}

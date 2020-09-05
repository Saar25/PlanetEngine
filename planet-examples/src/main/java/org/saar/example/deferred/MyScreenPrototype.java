package org.saar.example.deferred;

import org.saar.core.screen.ScreenPrototype;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ColourScreenImageBase;
import org.saar.core.screen.image.DepthScreenImageBase;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.fbos.attachment.colour.ColourAttachmentMS;
import org.saar.lwjgl.opengl.fbos.attachment.depth.DepthAttachmentMS;

public class MyScreenPrototype implements ScreenPrototype {

    @ScreenImageProperty(draw = true)
    private final ScreenImage colourImage = new ColourScreenImageBase(
            ColourAttachmentMS.withRenderBuffer(0, 16));

    @ScreenImageProperty(draw = true, read = true)
    private final ScreenImage normalImage = new ColourScreenImageBase(
            ColourAttachmentMS.withRenderBuffer(1, 16));

    @ScreenImageProperty
    private final ScreenImage depthImage = new DepthScreenImageBase(
            DepthAttachmentMS.withRenderBuffer(DepthFormatType.COMPONENT24, 16));
}

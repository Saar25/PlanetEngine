package org.saar.example.screen;

import org.saar.core.screen.ScreenPrototype;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ScreenImage;
import org.saar.core.screen.image.ScreenImageBase;
import org.saar.lwjgl.opengl.fbos.attachment.colour.ColourAttachmentMS;
import org.saar.lwjgl.opengl.fbos.attachment.depth.DepthAttachmentMS;

public class MyScreenPrototype implements ScreenPrototype {

    @ScreenImageProperty(draw = true, read = true)
    private final ScreenImage colourImage = new ScreenImageBase(ColourAttachmentMS.withTexture(0, 16));

    @ScreenImageProperty
    private final ScreenImage depthImage = new ScreenImageBase(DepthAttachmentMS.withTexture(16));
}

package org.saar.example.screen;

import org.saar.core.screen.ScreenPrototype;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.DepthScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment;
import org.saar.lwjgl.opengl.fbos.attachment.allocation.MultisampledAllocationStrategy;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.RenderBufferAttachmentBuffer;

public class MyScreenPrototype implements ScreenPrototype {

    @ScreenImageProperty(draw = true, read = true)
    private final ScreenImage colourImage = new ColourScreenImage(new ColourAttachment(0,
            new RenderBufferAttachmentBuffer(InternalFormat.RGBA8),
            new MultisampledAllocationStrategy(4)));

    @ScreenImageProperty(draw = true)
    private final ScreenImage depthImage = new DepthScreenImage(new DepthAttachment(
            new RenderBufferAttachmentBuffer(InternalFormat.DEPTH24),
            new MultisampledAllocationStrategy(4)));
}

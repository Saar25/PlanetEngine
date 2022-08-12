package org.saar.example.screen;

import org.saar.core.screen.ScreenPrototype;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ScreenImage;
import org.saar.core.screen.image.SimpleScreenImage;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbo.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbo.attachment.DepthAttachment;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.MultisampledAllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer;

public class MyScreenPrototype implements ScreenPrototype {

    @ScreenImageProperty(draw = true, read = true)
    private final ScreenImage colourImage = new SimpleScreenImage(new ColourAttachment(0,
            new RenderBufferAttachmentBuffer(InternalFormat.RGBA8),
            new MultisampledAllocationStrategy(4)));

    @ScreenImageProperty(draw = true)
    private final ScreenImage depthImage = new SimpleScreenImage(new DepthAttachment(
            new RenderBufferAttachmentBuffer(InternalFormat.DEPTH24),
            new MultisampledAllocationStrategy(4)));
}

package org.saar.example.screen;

import org.saar.core.screen.ScreenPrototype;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.DepthScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment;
import org.saar.lwjgl.opengl.fbos.attachment.allocation.SimpleRenderBufferAllocation;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.RenderBufferAttachmentBuffer;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;

public class MyScreenPrototype implements ScreenPrototype {

    @ScreenImageProperty
    private final ScreenImage colourImage = new ColourScreenImage(
            new ColourAttachment(0, new RenderBufferAttachmentBuffer(RenderBuffer.create(),
                    new SimpleRenderBufferAllocation(InternalFormat.RGBA8))));

    @ScreenImageProperty
    private final ScreenImage depthImage = new DepthScreenImage(
            new DepthAttachment(new RenderBufferAttachmentBuffer(RenderBuffer.create(),
                    new SimpleRenderBufferAllocation(InternalFormat.DEPTH24))));
}

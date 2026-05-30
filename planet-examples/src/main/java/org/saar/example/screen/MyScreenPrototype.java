package org.saar.example.screen;

import org.saar.core.screen.ScreenImagePrototype;
import org.saar.core.screen.ScreenPrototype;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex;
import org.saar.lwjgl.opengl.fbo.attachment.index.DepthAttachmentIndex;

public class MyScreenPrototype implements ScreenPrototype {

    @ScreenImageProperty
    private final ScreenImagePrototype colourImage = new ScreenImagePrototype(
        new ColourAttachmentIndex(0),
        new RenderBufferAttachmentBuffer(InternalFormat.RGBA8), true, true);

    @ScreenImageProperty
    private final ScreenImagePrototype depthImage = new ScreenImagePrototype(
        DepthAttachmentIndex.INSTANCE,
        new RenderBufferAttachmentBuffer(InternalFormat.DEPTH24), true, false);
}

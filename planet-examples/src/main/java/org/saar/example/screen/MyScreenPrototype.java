package org.saar.example.screen;

import org.saar.core.screen.ScreenImagePrototype;
import org.saar.core.screen.ScreenPrototype;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.index.BasicAttachmentIndex;
import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex;

public class MyScreenPrototype implements ScreenPrototype {

    @ScreenImageProperty
    private final ScreenImagePrototype colourImage = new ScreenImagePrototype(
            new ColourAttachmentIndex(0),
            new RenderBufferAttachmentBuffer(InternalFormat.RGBA8), true, true);

    @ScreenImageProperty
    private final ScreenImagePrototype depthImage = new ScreenImagePrototype(
            new BasicAttachmentIndex(AttachmentType.DEPTH),
            new RenderBufferAttachmentBuffer(InternalFormat.DEPTH24), true, false);
}

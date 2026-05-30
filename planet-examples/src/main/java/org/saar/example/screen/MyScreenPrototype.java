package org.saar.example.screen;

import org.jetbrains.annotations.NotNull;
import org.saar.core.screen.ScreenImagePrototype;
import org.saar.core.screen.ScreenPrototype;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex;
import org.saar.lwjgl.opengl.fbo.attachment.index.DepthAttachmentIndex;

import java.util.Arrays;
import java.util.Collection;

public class MyScreenPrototype implements ScreenPrototype {
    @Override
    public @NotNull Collection<@NotNull ScreenImagePrototype> getScreenImages() {
        return Arrays.asList(
            new ScreenImagePrototype(
                new ColourAttachmentIndex(0),
                new RenderBufferAttachmentBuffer(InternalFormat.RGBA8),
                true,
                true),
            new ScreenImagePrototype(
                DepthAttachmentIndex.INSTANCE,
                new RenderBufferAttachmentBuffer(InternalFormat.DEPTH24),
                true,
                false)
        );
    }
}

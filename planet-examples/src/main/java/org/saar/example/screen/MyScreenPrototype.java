package org.saar.example.screen;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
                true),
            new ScreenImagePrototype(
                DepthAttachmentIndex.INSTANCE,
                new RenderBufferAttachmentBuffer(InternalFormat.DEPTH24),
                true)
        );
    }

    @Override
    public @Nullable ColourAttachmentIndex getReadIndex() {
        return new ColourAttachmentIndex(0);
    }
}

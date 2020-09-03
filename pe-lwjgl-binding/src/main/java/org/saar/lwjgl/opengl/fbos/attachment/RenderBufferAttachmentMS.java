package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.RenderBuffer;

public class RenderBufferAttachmentMS implements AttachmentMS {

    private final AttachmentType type;
    private final int attachmentIndex;
    private final RenderBuffer renderBuffer;
    private final FormatType iFormat;
    private final int samples;

    public RenderBufferAttachmentMS(AttachmentType type, int attachmentIndex,
                                    RenderBuffer renderBuffer, FormatType iFormat, int samples) {
        this.type = type;
        this.attachmentIndex = attachmentIndex;
        this.renderBuffer = renderBuffer;
        this.iFormat = iFormat;
        this.samples = samples;
    }

    /**
     * Creates a new colour multisampled render buffer attachment
     *
     * @return the created depth attachment
     */
    public static RenderBufferAttachmentMS ofColour(int index, FormatType format, int samples) {
        final RenderBuffer renderBuffer = RenderBuffer.create();
        return new RenderBufferAttachmentMS(AttachmentType.COLOUR,
                index, renderBuffer, format, samples);
    }

    /**
     * Creates a new depth multisampled render buffer attachment
     *
     * @return the created depth attachment
     */
    public static RenderBufferAttachmentMS ofDepth(FormatType format, int samples) {
        final RenderBuffer renderBuffer = RenderBuffer.create();
        return new RenderBufferAttachmentMS(AttachmentType.DEPTH, 0,
                renderBuffer, format, samples);
    }

    public RenderBuffer getRenderBuffer() {
        return this.renderBuffer;
    }

    @Override
    public int getAttachmentPoint() {
        return type.get() + attachmentIndex;
    }

    @Override
    public void init(ReadOnlyFbo fbo) {
        getRenderBuffer().loadStorageMultisample(fbo.getWidth(), fbo.getHeight(), iFormat, samples);
        getRenderBuffer().attachToFbo(getAttachmentPoint());
    }

    @Override
    public void delete() {
        getRenderBuffer().delete();
    }
}

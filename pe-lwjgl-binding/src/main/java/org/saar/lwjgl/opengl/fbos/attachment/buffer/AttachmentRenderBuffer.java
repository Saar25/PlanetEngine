package org.saar.lwjgl.opengl.fbos.attachment.buffer;

import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;

public class AttachmentRenderBuffer implements AttachmentBuffer {

    private final RenderBuffer renderBuffer;
    private final FormatType iFormat;

    public AttachmentRenderBuffer(RenderBuffer renderBuffer) {
        this(renderBuffer, FormatType.RGBA8);
    }

    public AttachmentRenderBuffer(RenderBuffer renderBuffer, FormatType iFormat) {
        this.renderBuffer = renderBuffer;
        this.iFormat = iFormat;
    }

    public static AttachmentRenderBuffer create() {
        final RenderBuffer renderBuffer = RenderBuffer.create();
        return new AttachmentRenderBuffer(renderBuffer);
    }

    public static AttachmentRenderBuffer create(FormatType iFormat) {
        final RenderBuffer renderBuffer = RenderBuffer.create();
        return new AttachmentRenderBuffer(renderBuffer, iFormat);
    }

    private RenderBuffer getRenderBuffer() {
        return this.renderBuffer;
    }

    @Override
    public void allocate(int width, int height) {
        getRenderBuffer().loadStorage(width, height, this.iFormat);
    }

    @Override
    public void allocateMultisample(int width, int height, int samples) {
        getRenderBuffer().loadStorageMultisample(width, height, this.iFormat, samples);
    }

    @Override
    public void attachToFbo(int attachment) {
        getRenderBuffer().attachToFbo(attachment);
    }

    @Override
    public void delete() {
        getRenderBuffer().delete();
    }
}

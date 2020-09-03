package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.RenderBuffer;

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

    private RenderBuffer getRenderBuffer() {
        return this.renderBuffer;
    }

    @Override
    public void allocate(int width, int height) {
        getRenderBuffer().loadStorage(width, height, FormatType.RGBA8);
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

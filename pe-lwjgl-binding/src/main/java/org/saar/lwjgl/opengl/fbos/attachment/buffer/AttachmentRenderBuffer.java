package org.saar.lwjgl.opengl.fbos.attachment.buffer;

import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;

public class AttachmentRenderBuffer implements AttachmentBuffer {

    private final RenderBuffer renderBuffer;
    private final InternalFormat iFormat;

    public AttachmentRenderBuffer(RenderBuffer renderBuffer, InternalFormat iFormat) {
        this.renderBuffer = renderBuffer;
        this.iFormat = iFormat;
    }

    @Override
    public void allocate(int width, int height) {
        this.renderBuffer.loadStorage(width, height, this.iFormat);
    }

    @Override
    public void allocateMultisample(int width, int height, int samples) {
        this.renderBuffer.loadStorageMultisample(width, height, this.iFormat, samples);
    }

    @Override
    public void attachToFbo(int attachment) {
        this.renderBuffer.attachToFbo(attachment);
    }

    @Override
    public void delete() {
        this.renderBuffer.delete();
    }
}

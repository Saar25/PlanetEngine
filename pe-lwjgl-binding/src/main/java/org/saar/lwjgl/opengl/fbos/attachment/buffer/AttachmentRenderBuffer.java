package org.saar.lwjgl.opengl.fbos.attachment.buffer;

import org.saar.lwjgl.opengl.constants.IInternalFormat;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;

public class AttachmentRenderBuffer implements AttachmentBuffer {

    private final RenderBuffer renderBuffer;
    private final IInternalFormat iFormat;

    public AttachmentRenderBuffer(RenderBuffer renderBuffer, IInternalFormat iFormat) {
        this.renderBuffer = renderBuffer;
        this.iFormat = iFormat;
    }

    public static AttachmentRenderBuffer create(IInternalFormat iFormat) {
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

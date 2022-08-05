package org.saar.lwjgl.opengl.fbos.attachment.buffer;

import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbos.attachment.index.AttachmentIndex;
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
        throw new UnsupportedOperationException("Deprecated");
    }

    @Override
    public void attachToFbo(AttachmentIndex index) {
        this.renderBuffer.attachToFbo(index.getValue());
    }

    @Override
    public void delete() {
        this.renderBuffer.delete();
    }
}

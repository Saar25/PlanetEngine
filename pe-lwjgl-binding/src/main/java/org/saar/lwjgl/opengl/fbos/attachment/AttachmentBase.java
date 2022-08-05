package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;

public abstract class AttachmentBase implements Attachment {

    private final AttachmentBuffer buffer;

    public AttachmentBase(AttachmentBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public final void init(ReadOnlyFbo fbo) {
        this.buffer.allocate(fbo.getWidth(), fbo.getHeight());
        this.buffer.attachToFbo(getIndex());
    }

    @Override
    public final void delete() {
        this.buffer.delete();
    }
}

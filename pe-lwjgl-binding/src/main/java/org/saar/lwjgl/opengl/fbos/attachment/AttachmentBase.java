package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;

public abstract class AttachmentBase implements Attachment {

    private final AttachmentIndex index;
    private final AttachmentBuffer buffer;

    public AttachmentBase(AttachmentIndex index, AttachmentBuffer buffer) {
        this.index = index;
        this.buffer = buffer;
    }

    @Override
    public final AttachmentIndex getIndex() {
        return this.index;
    }

    @Override
    public final void init() {
        this.buffer.allocate();
        this.buffer.attachToFbo(this.index);
    }

    @Override
    public final void initMS() {
        this.buffer.allocate();
        this.buffer.attachToFbo(this.index);
    }

    @Override
    public final void delete() {
        this.buffer.delete();
    }
}

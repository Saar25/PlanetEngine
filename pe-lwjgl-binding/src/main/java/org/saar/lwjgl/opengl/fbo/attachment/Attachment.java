package org.saar.lwjgl.opengl.fbo.attachment;

import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;

public class Attachment implements IAttachment {

    private final AttachmentBuffer buffer;
    private final AllocationStrategy allocation;

    public Attachment(AttachmentBuffer buffer, AllocationStrategy allocation) {
        this.buffer = buffer;
        this.allocation = allocation;
    }

    @Override
    public void init(AttachmentIndex index) {
        this.buffer.attachToFbo(index);
    }

    @Override
    public void allocate(int width, int height) {
        this.allocation.allocate(this.buffer, width, height);
    }

    @Override
    public void delete() {
        this.buffer.delete();
    }
}

package org.saar.lwjgl.opengl.fbo.attachment;

import org.saar.lwjgl.opengl.fbo.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;
import org.saar.lwjgl.opengl.fbo.attachment.index.BasicAttachmentIndex;

public class DepthAttachment implements Attachment {

    private final AttachmentIndex index = new BasicAttachmentIndex(AttachmentType.DEPTH);

    private final AttachmentBuffer buffer;
    private final AllocationStrategy allocation;

    public DepthAttachment(AttachmentBuffer buffer, AllocationStrategy allocation) {
        this.buffer = buffer;
        this.allocation = allocation;
    }

    @Override
    public AttachmentIndex getIndex() {
        return this.index;
    }

    @Override
    public final void init(ReadOnlyFbo fbo) {
        this.allocation.allocate(this.buffer, fbo.getWidth(), fbo.getHeight());
        this.buffer.attachToFbo(getIndex());
    }

    @Override
    public final void delete() {
        this.buffer.delete();
    }
}

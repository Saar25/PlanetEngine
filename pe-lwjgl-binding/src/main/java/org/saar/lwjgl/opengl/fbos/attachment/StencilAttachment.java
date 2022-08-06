package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.attachment.allocation.AllocationStrategy;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.index.AttachmentIndex;
import org.saar.lwjgl.opengl.fbos.attachment.index.BasicAttachmentIndex;

public class StencilAttachment implements Attachment {

    private final AttachmentIndex index = new BasicAttachmentIndex(AttachmentType.STENCIL);

    private final AttachmentBuffer buffer;
    private final AllocationStrategy allocation;

    public StencilAttachment(AttachmentBuffer buffer, AllocationStrategy allocation) {
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

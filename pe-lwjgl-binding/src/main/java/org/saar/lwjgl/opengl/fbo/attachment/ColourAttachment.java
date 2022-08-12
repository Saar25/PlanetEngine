package org.saar.lwjgl.opengl.fbo.attachment;

import org.saar.lwjgl.opengl.fbo.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex;

public class ColourAttachment implements Attachment, IColourAttachment {

    private final ColourAttachmentIndex index;

    private final AttachmentBuffer buffer;
    private final AllocationStrategy allocation;

    public ColourAttachment(int index, AttachmentBuffer buffer, AllocationStrategy allocation) {
        this.index = new ColourAttachmentIndex(index);
        this.buffer = buffer;
        this.allocation = allocation;
    }

    @Override
    public ColourAttachmentIndex getIndex() {
        return this.index;
    }

    @Override
    public void init(ReadOnlyFbo fbo) {
        this.allocation.allocate(this.buffer, fbo.getWidth(), fbo.getHeight());
        this.buffer.attachToFbo(getIndex());
    }

    @Override
    public void delete() {
        this.buffer.delete();
    }
}

package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.index.AttachmentIndex;
import org.saar.lwjgl.opengl.fbos.attachment.index.BasicAttachmentIndex;

public class DepthStencilAttachment extends AttachmentBase implements Attachment {

    private final AttachmentIndex index = new BasicAttachmentIndex(AttachmentType.DEPTH_STENCIL);

    public DepthStencilAttachment(AttachmentBuffer buffer) {
        super(buffer);
    }

    @Override
    public AttachmentIndex getIndex() {
        return this.index;
    }
}

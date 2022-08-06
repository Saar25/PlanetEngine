package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.index.ColourAttachmentIndex;

public class ColourAttachment extends AttachmentBase implements Attachment, IColourAttachment {

    private final ColourAttachmentIndex index;

    public ColourAttachment(int index, AttachmentBuffer buffer) {
        super(buffer);
        this.index = new ColourAttachmentIndex(index);
    }

    @Override
    public ColourAttachmentIndex getIndex() {
        return this.index;
    }
}

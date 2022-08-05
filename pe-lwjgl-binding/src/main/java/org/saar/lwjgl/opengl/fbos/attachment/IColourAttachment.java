package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.fbos.attachment.index.ColourAttachmentIndex;

public interface IColourAttachment extends Attachment {

    @Override
    ColourAttachmentIndex getIndex();

}

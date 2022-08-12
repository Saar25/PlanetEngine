package org.saar.lwjgl.opengl.fbo.attachment;

import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex;

public interface IColourAttachment extends Attachment {

    @Override
    ColourAttachmentIndex getIndex();

}

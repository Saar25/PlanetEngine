package org.saar.lwjgl.opengl.fbo.attachment;

import org.saar.lwjgl.opengl.fbo.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;

public interface Attachment {

    AttachmentIndex getIndex();

    void init(ReadOnlyFbo fbo);

    void delete();

}

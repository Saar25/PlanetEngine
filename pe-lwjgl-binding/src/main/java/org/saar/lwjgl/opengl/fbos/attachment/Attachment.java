package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.attachment.index.AttachmentIndex;

public interface Attachment {

    AttachmentIndex getIndex();

    void init(ReadOnlyFbo fbo);

    void delete();

}

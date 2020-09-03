package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;

public interface Attachment {

    int getAttachmentPoint();

    AttachmentType getAttachmentType();

    void init(ReadOnlyFbo fbo);

    void delete();

}

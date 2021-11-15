package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;

public interface Attachment {

    AttachmentIndex getIndex();

    void init(ReadOnlyFbo fbo);

    void initMS(ReadOnlyFbo fbo, int samples);

    void delete();

}

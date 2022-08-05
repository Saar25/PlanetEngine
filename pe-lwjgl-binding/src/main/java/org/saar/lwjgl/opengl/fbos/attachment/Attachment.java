package org.saar.lwjgl.opengl.fbos.attachment;

public interface Attachment {

    AttachmentIndex getIndex();

    void init();

    void delete();

}

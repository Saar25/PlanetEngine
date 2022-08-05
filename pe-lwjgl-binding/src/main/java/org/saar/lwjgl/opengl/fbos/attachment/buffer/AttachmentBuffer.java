package org.saar.lwjgl.opengl.fbos.attachment.buffer;

import org.saar.lwjgl.opengl.fbos.attachment.AttachmentIndex;

public interface AttachmentBuffer {

    void allocate(int width, int height);

    void attachToFbo(AttachmentIndex index);

    void delete();

}

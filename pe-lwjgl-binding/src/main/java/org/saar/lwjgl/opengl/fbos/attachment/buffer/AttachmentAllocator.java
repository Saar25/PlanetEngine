package org.saar.lwjgl.opengl.fbos.attachment.buffer;

public interface AttachmentAllocator {

    void allocate(AttachmentBuffer buffer, int width, int height);

}

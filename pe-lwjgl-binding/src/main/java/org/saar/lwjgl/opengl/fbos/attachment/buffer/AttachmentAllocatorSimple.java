package org.saar.lwjgl.opengl.fbos.attachment.buffer;

public class AttachmentAllocatorSimple implements AttachmentAllocator {

    @Override
    public void allocate(AttachmentBuffer buffer, int width, int height) {
        buffer.allocate(width, height);
    }
}

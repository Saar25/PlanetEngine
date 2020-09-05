package org.saar.lwjgl.opengl.fbos.attachment.buffer;

public class AttachmentAllocatorMS implements AttachmentAllocator {

    private final int samples;

    public AttachmentAllocatorMS(int samples) {
        this.samples = samples;
    }

    @Override
    public void allocate(AttachmentBuffer buffer, int width, int height) {
        buffer.allocateMultisample(width, height, samples);
    }
}

package org.saar.lwjgl.opengl.fbos.attachment;

public interface AttachmentBuffer {

    void allocate(int width, int height);

    void allocateMultisample(int width, int height, int samples);

    void attachToFbo(int attachment);

    void delete();

}

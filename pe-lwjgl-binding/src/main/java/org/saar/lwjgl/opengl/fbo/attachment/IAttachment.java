package org.saar.lwjgl.opengl.fbo.attachment;

import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;

public interface IAttachment {

    void attach(int fbo, AttachmentIndex index);

    void allocate(int width, int height);

    void delete();

}

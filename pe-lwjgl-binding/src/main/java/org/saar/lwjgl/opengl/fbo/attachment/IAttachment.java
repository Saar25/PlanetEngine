package org.saar.lwjgl.opengl.fbo.attachment;

import org.saar.lwjgl.opengl.fbo.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;

public interface IAttachment {

    void init(ReadOnlyFbo fbo, AttachmentIndex index);

    void delete();

}

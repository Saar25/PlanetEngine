package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.attachment.Attachment;

public interface ScreenImage {

    Attachment getAttachment();

    void init(ReadOnlyFbo fbo);

    void delete();
}

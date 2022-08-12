package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbo.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbo.attachment.Attachment;

public interface ScreenImage {

    Attachment getAttachment();

    default void init(ReadOnlyFbo fbo) {
        getAttachment().init(fbo);
    }

    default void delete() {
        getAttachment().delete();
    }
}

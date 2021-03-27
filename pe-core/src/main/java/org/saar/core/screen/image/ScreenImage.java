package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.attachment.Attachment;

public interface ScreenImage {

    Attachment getAttachment();

    default void init(ReadOnlyFbo fbo) {
        getAttachment().init(fbo);
    }

    default void delete() {
        getAttachment().delete();
    }
}

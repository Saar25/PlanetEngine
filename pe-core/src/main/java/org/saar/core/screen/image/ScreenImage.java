package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbo.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbo.attachment.IAttachment;
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;

public interface ScreenImage {

    IAttachment getAttachment();

    default void init(ReadOnlyFbo fbo, AttachmentIndex index) {
        getAttachment().init(fbo, index);
    }

    default void delete() {
        getAttachment().delete();
    }
}

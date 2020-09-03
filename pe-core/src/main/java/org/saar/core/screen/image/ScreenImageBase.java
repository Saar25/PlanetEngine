package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.attachment.Attachment;

public class ScreenImageBase implements ScreenImage {

    private final Attachment attachment;

    public ScreenImageBase(Attachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public void init(ReadOnlyFbo fbo) {
        this.attachment.init(fbo);
    }

    @Override
    public void delete() {
        this.attachment.delete();
    }
}

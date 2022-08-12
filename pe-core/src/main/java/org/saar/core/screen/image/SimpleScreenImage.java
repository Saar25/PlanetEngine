package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbo.attachment.Attachment;

public class SimpleScreenImage implements ScreenImage {

    private final Attachment attachment;

    public SimpleScreenImage(Attachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public Attachment getAttachment() {
        return this.attachment;
    }
}

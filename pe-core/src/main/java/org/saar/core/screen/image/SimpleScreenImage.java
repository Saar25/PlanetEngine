package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbo.attachment.IAttachment;

public class SimpleScreenImage implements ScreenImage {

    private final IAttachment attachment;

    public SimpleScreenImage(IAttachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public IAttachment getAttachment() {
        return this.attachment;
    }
}

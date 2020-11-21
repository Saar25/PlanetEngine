package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment;

public class DepthScreenImage implements ScreenImage {

    private final DepthAttachment attachment;

    public DepthScreenImage(DepthAttachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public DepthAttachment getAttachment() {
        return this.attachment;
    }
}

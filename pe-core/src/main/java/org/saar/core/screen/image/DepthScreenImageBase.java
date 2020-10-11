package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment;

public class DepthScreenImageBase extends ScreenImageBase implements DepthScreenImage {

    private final DepthAttachment attachment;

    public DepthScreenImageBase(DepthAttachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public DepthAttachment getAttachment() {
        return this.attachment;
    }
}

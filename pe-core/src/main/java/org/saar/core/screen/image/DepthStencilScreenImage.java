package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.attachment.DepthStencilAttachment;

public class DepthStencilScreenImage implements ScreenImage {

    private final DepthStencilAttachment attachment;

    public DepthStencilScreenImage(DepthStencilAttachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public DepthStencilAttachment getAttachment() {
        return this.attachment;
    }
}

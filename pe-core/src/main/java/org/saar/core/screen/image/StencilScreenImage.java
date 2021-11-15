package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.attachment.StencilAttachment;

public class StencilScreenImage implements ScreenImage {

    private final StencilAttachment attachment;

    public StencilScreenImage(StencilAttachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public StencilAttachment getAttachment() {
        return this.attachment;
    }
}

package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;

public class ColourScreenImage implements ScreenImage {

    private final ColourAttachment attachment;

    public ColourScreenImage(ColourAttachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public ColourAttachment getAttachment() {
        return this.attachment;
    }
}

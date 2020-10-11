package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;

public class ColourScreenImageBase extends ScreenImageBase implements ColourScreenImage {

    private final ColourAttachment attachment;

    public ColourScreenImageBase(ColourAttachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public ColourAttachment getAttachment() {
        return this.attachment;
    }
}

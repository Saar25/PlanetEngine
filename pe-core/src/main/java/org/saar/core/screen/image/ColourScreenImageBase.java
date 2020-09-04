package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.attachment.colour.IColourAttachment;

public class ColourScreenImageBase extends ScreenImageBase implements ColourScreenImage {

    private final IColourAttachment attachment;

    public ColourScreenImageBase(IColourAttachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public IColourAttachment getAttachment() {
        return this.attachment;
    }
}

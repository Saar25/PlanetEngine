package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.attachment.colour.IColourAttachment;

public interface ColourScreenImage extends ScreenImage {

    @Override
    IColourAttachment getAttachment();

}

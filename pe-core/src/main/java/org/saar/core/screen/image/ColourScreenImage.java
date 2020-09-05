package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;

public interface ColourScreenImage extends ScreenImage {

    @Override
    ColourAttachment getAttachment();

}

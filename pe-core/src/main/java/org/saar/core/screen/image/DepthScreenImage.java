package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment;

public interface DepthScreenImage extends ScreenImage {

    @Override
    DepthAttachment getAttachment();

}

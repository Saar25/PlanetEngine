package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.attachment.depth.IDepthAttachment;

public interface DepthScreenImage extends ScreenImage {

    @Override
    IDepthAttachment getAttachment();

}

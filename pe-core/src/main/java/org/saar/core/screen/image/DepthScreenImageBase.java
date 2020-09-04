package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.attachment.depth.IDepthAttachment;

public class DepthScreenImageBase extends ScreenImageBase implements DepthScreenImage {

    private final IDepthAttachment attachment;

    public DepthScreenImageBase(IDepthAttachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public IDepthAttachment getAttachment() {
        return this.attachment;
    }
}

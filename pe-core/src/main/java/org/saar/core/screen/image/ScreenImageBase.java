package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;

public abstract class ScreenImageBase implements ScreenImage {

    @Override
    public void init(ReadOnlyFbo fbo) {
        getAttachment().init(fbo);
    }

    @Override
    public void delete() {
        getAttachment().delete();
    }
}

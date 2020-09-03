package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;

public interface ScreenImage {

    void init(ReadOnlyFbo fbo);

    void delete();
}

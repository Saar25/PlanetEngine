package org.saar.core.screen.image;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public interface ScreenImage {

    void init(ReadOnlyFbo fbo);

    ReadOnlyTexture getTexture();

    void delete();
}

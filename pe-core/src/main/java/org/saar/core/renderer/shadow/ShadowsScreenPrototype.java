package org.saar.core.renderer.shadow;

import org.saar.core.screen.ScreenPrototype;
import org.saar.lwjgl.opengl.textures.Texture;

public interface ShadowsScreenPrototype extends ScreenPrototype {

    Texture getDepthTexture();

}

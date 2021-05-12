package org.saar.core.renderer.forward;

import org.saar.core.screen.ScreenPrototype;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public interface ForwardScreenPrototype extends ScreenPrototype {

    ReadOnlyTexture getColourTexture();

    ReadOnlyTexture getDepthTexture();

}

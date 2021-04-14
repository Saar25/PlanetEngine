package org.saar.core.renderer.deferred;

import org.saar.core.screen.ScreenPrototype;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public interface DeferredScreenPrototype extends ScreenPrototype {

    ReadOnlyTexture getColourTexture();

    ReadOnlyTexture getNormalTexture();

    ReadOnlyTexture getDepthTexture();

    default DeferredRenderingBuffers asBuffers() {
        return new DeferredRenderingBuffers(
                getColourTexture(),
                getNormalTexture(),
                getDepthTexture()
        );
    }
}

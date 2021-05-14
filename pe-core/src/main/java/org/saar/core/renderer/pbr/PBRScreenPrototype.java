package org.saar.core.renderer.pbr;

import org.saar.core.screen.ScreenPrototype;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public interface PBRScreenPrototype extends ScreenPrototype {

    ReadOnlyTexture getColourTexture();

    ReadOnlyTexture getNormalTexture();

    ReadOnlyTexture getReflectivityTexture();

    ReadOnlyTexture getDepthTexture();

    default PBRRenderingBuffers asBuffers() {
        return new PBRRenderingBuffers(
                getColourTexture(),
                getNormalTexture(),
                getReflectivityTexture(),
                getDepthTexture()
        );
    }
}

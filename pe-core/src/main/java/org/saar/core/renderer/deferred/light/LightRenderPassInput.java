package org.saar.core.renderer.deferred.light;

import org.saar.core.renderer.deferred.RenderPassInput;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public class LightRenderPassInput implements RenderPassInput {

    protected final ReadOnlyTexture colourTexture;
    protected final ReadOnlyTexture normalTexture;
    protected final ReadOnlyTexture depthTexture;

    public LightRenderPassInput(ReadOnlyTexture colourTexture, ReadOnlyTexture normalTexture, ReadOnlyTexture depthTexture) {
        this.colourTexture = colourTexture;
        this.normalTexture = normalTexture;
        this.depthTexture = depthTexture;
    }
}

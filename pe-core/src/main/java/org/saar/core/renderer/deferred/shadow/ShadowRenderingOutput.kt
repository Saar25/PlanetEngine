package org.saar.core.renderer.deferred.shadow;

import org.saar.core.renderer.RenderingOutput;
import org.saar.core.screen.Screen;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public class ShadowRenderingOutput implements RenderingOutput {

    private final Screen screen;

    public ShadowRenderingOutput(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void to(Screen screen) {
        this.screen.copyTo(screen);
    }

    public ReadOnlyTexture getShowMap() {
        return null;
    }

}

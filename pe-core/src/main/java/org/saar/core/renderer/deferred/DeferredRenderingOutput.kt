package org.saar.core.renderer.deferred;

import org.saar.core.renderer.RenderingOutput;
import org.saar.core.screen.Screen;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public class DeferredRenderingOutput implements RenderingOutput {

    private final Screen screen;

    public DeferredRenderingOutput(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void to(Screen screen) {
        this.screen.copyTo(screen);
    }

}

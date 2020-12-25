package org.saar.core.renderer.forward;

import org.saar.core.renderer.RenderingOutput;
import org.saar.core.screen.Screen;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public class ForwardRenderingOutput implements RenderingOutput {

    private final Screen screen;

    public ForwardRenderingOutput(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void to(Screen screen) {
        this.screen.copyTo(screen);
    }

    public ReadOnlyTexture getColourTexture() {
        return null;
    }

}

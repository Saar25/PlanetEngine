package org.saar.core.renderer.forward;

import org.saar.core.camera.ICamera;
import org.saar.core.renderer.*;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.fbos.Fbo;

public class ForwardRenderingPath implements RenderingPath {

    private final ForwardScreenPrototype prototype;
    private final OffScreen screen;
    private final ICamera camera;
    private final RenderersHelper helper;

    public ForwardRenderingPath(ForwardScreenPrototype prototype, ICamera camera, Renderer... renderers) {
        this.prototype = prototype;
        this.screen = Screens.fromPrototype(prototype, Fbo.create(0, 0));

        this.camera = camera;
        this.helper = RenderersHelper.of(renderers);
    }

    private void checkSize() {
        final int width = MainScreen.getInstance().getWidth();
        final int height = MainScreen.getInstance().getHeight();
        if (this.screen.getWidth() != width || this.screen.getHeight() != height) {
            this.screen.resize(width, height);
        }
    }

    @Override
    public RenderingOutput render() {
        checkSize();

        this.screen.setAsDraw();

        this.helper.render(new RenderContextBase(this.camera));

        return new ForwardRenderingOutput(this.screen, this.prototype.getColourTexture());
    }

    @Override
    public void delete() {
        this.helper.delete();
        this.screen.delete();
    }
}

package org.saar.core.renderer.forward;

import org.saar.core.camera.ICamera;
import org.saar.core.renderer.*;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.OffScreen;

public class ForwardRenderingPath implements RenderingPath {

    private final OffScreen screen;
    private final ICamera camera;

    private RenderersHelper helper = RenderersHelper.empty();

    public ForwardRenderingPath(OffScreen screen, ICamera camera) {
        this.screen = screen;
        this.camera = camera;
    }

    public void addRenderer(Renderer renderer) {
        this.helper = this.helper.addRenderer(renderer);
    }

    public void removeRenderer(Renderer renderer) {
        this.helper = this.helper.removeRenderer(renderer);
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

        return new ForwardRenderingOutput(this.screen);
    }

    @Override
    public void delete() {
        this.helper.delete();
        this.screen.delete();
    }
}

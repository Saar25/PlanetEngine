package org.saar.core.renderer.forward;

import org.saar.core.camera.ICamera;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.RenderersGroup;
import org.saar.core.renderer.RenderingOutput;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.fbos.Fbo;

public class ForwardRenderingPath implements RenderingPath {

    private final ForwardScreenPrototype prototype;
    private final OffScreen screen;
    private final ICamera camera;
    private final RenderersGroup renderers;

    public ForwardRenderingPath(ForwardScreenPrototype prototype, ICamera camera, RenderersGroup renderers) {
        this.prototype = prototype;
        this.screen = Screens.fromPrototype(prototype, Fbo.create(0, 0));

        this.camera = camera;
        this.renderers = renderers;
    }

    @Override
    public RenderingOutput render() {
        this.screen.resizeToMainScreen();

        this.screen.setAsDraw();

        this.renderers.render(new RenderContextBase(this.camera));

        return new ForwardRenderingOutput(this.screen, this.prototype.getColourTexture());
    }

    @Override
    public void delete() {
        this.renderers.delete();
        this.screen.delete();
    }
}

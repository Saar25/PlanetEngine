package org.saar.core.renderer.deferred;

import org.saar.core.camera.ICamera;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.RenderersGroup;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class DeferredRenderingPath implements RenderingPath {

    private final OffScreen screen;
    private final DeferredScreenPrototype prototype;

    private final ICamera camera;
    private final RenderersGroup renderers;
    private final RenderPassesPipeline pipeline;

    public DeferredRenderingPath(DeferredScreenPrototype prototype, ICamera camera,
                                 RenderersGroup renderers, RenderPassesPipeline pipeline) {
        this.prototype = prototype;
        this.screen = Screens.fromPrototype(prototype, Fbo.create(0, 0));

        this.camera = camera;
        this.renderers = renderers;
        this.pipeline = pipeline;
    }

    @Override
    public DeferredRenderingOutput render() {
        this.screen.setAsDraw();
        this.screen.resizeToMainScreen();

        GlUtils.clearColourAndDepthBuffer();
        this.renderers.render(new RenderContextBase(this.camera));

        return this.pipeline.process(this.camera, this.prototype.asBuffers());
    }

    @Override
    public void delete() {
        this.screen.delete();
        this.renderers.delete();
        this.pipeline.delete();
    }
}

package org.saar.core.renderer.deferred;

import org.saar.core.camera.ICamera;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class DeferredRenderingPath implements RenderingPath {

    private final DeferredScreenPrototype prototype = new DeferredScreenPrototype();

    private final OffScreen screen = Screens.fromPrototype(this.prototype, Fbo.create(0, 0));

    private final ICamera camera;
    private final DeferredRenderNode renderNode;
    private final DeferredRenderPassesPipeline pipeline;

    public DeferredRenderingPath(ICamera camera, DeferredRenderNode renderNode,
                                 DeferredRenderPassesPipeline pipeline) {
        this.camera = camera;
        this.renderNode = renderNode;
        this.pipeline = pipeline;
    }

    @Override
    public DeferredRenderingOutput render() {
        this.screen.setAsDraw();
        this.screen.resizeToMainScreen();

        GlUtils.clearColourAndDepthBuffer();

        this.renderNode.renderDeferred(new RenderContextBase(this.camera));

        return this.pipeline.process(this.camera, this.prototype.asBuffers());
    }

    @Override
    public void delete() {
        this.screen.delete();
        this.renderNode.delete();
        this.pipeline.delete();
    }
}

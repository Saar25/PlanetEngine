package org.saar.core.renderer.pbr;

import org.saar.core.camera.ICamera;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class PBRenderingPath implements RenderingPath {

    private final PBScreenPrototype prototype = new PBScreenPrototype();

    private final OffScreen screen = Screens.fromPrototype(this.prototype, Fbo.create(0, 0));

    private final ICamera camera;
    private final PBRenderNode renderNode;
    private final PBRenderPassesPipeline pipeline;

    public PBRenderingPath(ICamera camera, PBRenderNode renderNode, PBRenderPassesPipeline pipeline) {
        this.camera = camera;
        this.renderNode = renderNode;
        this.pipeline = pipeline;
    }

    @Override
    public PBRenderingOutput render() {
        this.screen.setAsDraw();
        this.screen.resizeToMainScreen();

        GlUtils.clearColourAndDepthBuffer();

        this.renderNode.renderPBR(new RenderContextBase(this.camera));

        return this.pipeline.process(this.camera, this.prototype.asBuffers());
    }

    @Override
    public void delete() {
        this.screen.delete();
        this.renderNode.delete();
        this.pipeline.delete();
    }
}

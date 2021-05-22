package org.saar.core.renderer.pbr;

import org.saar.core.camera.ICamera;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class PBRRenderingPath implements RenderingPath {

    private final OffScreen screen;
    private final PBRScreenPrototype prototype;

    private final ICamera camera;
    private final PBRRenderNode renderNode;
    private final PBRRenderPassesPipeline pipeline;

    public PBRRenderingPath(ICamera camera, PBRRenderNode renderNode,
                            PBRRenderPassesPipeline pipeline) {
        this(new PBRScreenPrototypeDefault(), camera, renderNode, pipeline);
    }

    public PBRRenderingPath(PBRScreenPrototype prototype, ICamera camera,
                            PBRRenderNode renderNode, PBRRenderPassesPipeline pipeline) {
        this.prototype = prototype;
        this.screen = Screens.fromPrototype(prototype, Fbo.create(0, 0));

        this.camera = camera;
        this.renderNode = renderNode;
        this.pipeline = pipeline;
    }

    @Override
    public PBRRenderingOutput render() {
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

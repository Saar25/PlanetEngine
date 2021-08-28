package org.saar.core.renderer.pbr;

import org.saar.core.camera.ICamera;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.renderer.renderpass.RenderPassContext;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.constants.Comparator;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.stencil.*;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class PBRenderingPath implements RenderingPath {

    private final PBScreenPrototype prototype = new PBScreenPrototype();

    private final OffScreen screen = Screens.fromPrototype(this.prototype, Fbo.create(0, 0));

    private final StencilState stencilState = new StencilState(StencilOperation.REPLACE_ON_PASS,
            new StencilFunction(Comparator.ALWAYS, 1, 0xFF), StencilMask.UNCHANGED);

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

        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL);

        StencilTest.apply(this.stencilState);

        this.renderNode.renderPBR(new RenderContextBase(this.camera));

        final RenderPassContext context = new RenderPassContext(this.camera);
        this.pipeline.process(context, this.prototype.asBuffers());

        return new PBRenderingOutput(this.screen, this.prototype.asBuffers());
    }

    @Override
    public void delete() {
        this.screen.delete();
        this.renderNode.delete();
        this.pipeline.delete();
    }
}

package org.saar.core.renderer.deferred;

import org.saar.core.camera.ICamera;
import org.saar.core.renderer.*;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class DeferredRenderingPath implements RenderingPath {

    private final ICamera camera;

    private final OffScreen screen;
    private final DeferredRenderingBuffers buffers;
    private final DeferredRenderingPipeline pipeline;
    private RenderersHelper renderersHelper = RenderersHelper.empty();

    public DeferredRenderingPath(ICamera camera, DeferredScreenPrototype screen) {
        this.camera = camera;
        this.screen = Screens.fromPrototype(screen, fbo());
        this.buffers = new DeferredRenderingBuffers(
                screen.getColourTexture(),
                screen.getNormalTexture(),
                screen.getDepthTexture());
        this.pipeline = new DeferredRenderingPipeline(this.screen);
    }

    private static Fbo fbo() {
        final int width = MainScreen.getInstance().getWidth();
        final int height = MainScreen.getInstance().getHeight();
        return Fbo.create(width, height);
    }

    public void addRenderer(Renderer renderer) {
        this.renderersHelper = this.renderersHelper.addRenderer(renderer);
    }

    public void removeRenderer(Renderer renderer) {
        this.renderersHelper = this.renderersHelper.removeRenderer(renderer);
    }

    public void addRenderPass(RenderPass renderPass) {
        this.pipeline.addRenderPass(renderPass);
    }

    public void removeRenderPass(RenderPass renderPass) {
        this.pipeline.removeRenderPass(renderPass);
    }

    private void checkSize() {
        final int width = MainScreen.getInstance().getWidth();
        final int height = MainScreen.getInstance().getHeight();
        if (this.screen.getWidth() != width || this.screen.getHeight() != height) {
            this.screen.resize(width, height);
            this.pipeline.resize(width, height);
        }
    }

    @Override
    public DeferredRenderingOutput render() {
        checkSize();

        doFirstPass();
        doRenderPasses();

        return new DeferredRenderingOutput(this.screen);
    }

    private void doFirstPass() {
        final RenderContext context = new RenderContextBase(this.camera);

        this.screen.setAsDraw();
        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);
        this.renderersHelper.render(context);
    }

    private void doRenderPasses() {
        this.pipeline.render(this.buffers);
    }

    @Override
    public void delete() {
        this.screen.delete();
        this.pipeline.delete();
        this.renderersHelper.delete();
    }
}

package org.saar.core.renderer.deferred;

import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class DeferredRenderingPath implements RenderingPath {

    private final OffScreen screen;
    private final DeferredRenderingBuffers buffers;
    private final DeferredRenderingPipeline pipeline;

    public DeferredRenderingPath(DeferredScreenPrototype screen, RenderPass... renderPasses) {
        this.screen = Screens.fromPrototype(screen, fbo());
        this.buffers = new DeferredRenderingBuffers(
                screen.getColourTexture(),
                screen.getNormalTexture(),
                screen.getDepthTexture());
        this.pipeline = new DeferredRenderingPipeline(this.screen, renderPasses);
    }

    private static Fbo fbo() {
        final int width = MainScreen.getInstance().getWidth();
        final int height = MainScreen.getInstance().getHeight();
        return Fbo.create(width, height);
    }

    private void checkSize() {
        final int width = MainScreen.getInstance().getWidth();
        final int height = MainScreen.getInstance().getHeight();
        if (this.screen.getWidth() != width || this.screen.getHeight() != height) {
            this.screen.resize(width, height);
            this.pipeline.resize(width, height);
        }
    }

    public void bind() {
        this.screen.setAsDraw();
        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);
    }

    @Override
    public DeferredRenderingOutput render() {
        checkSize();

        this.pipeline.render(this.buffers);

        return new DeferredRenderingOutput(this.screen, this.buffers.getAlbedo());
    }

    @Override
    public void delete() {
        this.screen.delete();
        this.pipeline.delete();
    }
}

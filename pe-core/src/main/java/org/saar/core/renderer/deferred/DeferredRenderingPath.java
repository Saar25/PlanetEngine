package org.saar.core.renderer.deferred;

import org.saar.core.camera.ICamera;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.SimpleScreen;
import org.saar.core.screen.image.ColourScreenImage;
import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class DeferredRenderingPath implements RenderingPath {

    private final ICamera camera;
    private final DeferredRenderingPipeline pipeline;
    private final DeferredRenderingBuffers buffers;
    private final OffScreen screen;

    private final Texture colourTexture = Texture.create();

    public DeferredRenderingPath(ICamera camera, DeferredRenderingBuffers buffers, RenderPass... renderPasses) {
        this.camera = camera;
        this.pipeline = new DeferredRenderingPipeline(renderPasses);
        this.screen = screen(this.colourTexture);
        this.buffers = buffers;
    }

    private static OffScreen screen(Texture colourTexture) {
        final SimpleScreen screen = new SimpleScreen(fbo());
        final ColourScreenImage screenImage = new ColourScreenImage(ColourAttachment.withTexture(
                0, colourTexture, ColourFormatType.RGB16, FormatType.RGB, DataType.U_BYTE));
        screen.addScreenImage(screenImage);
        screen.setReadImages(screenImage);
        screen.setDrawImages(screenImage);
        return screen;
    }

    private static Fbo fbo() {
        final int width = MainScreen.getInstance().getWidth();
        final int height = MainScreen.getInstance().getHeight();
        return Fbo.create(width, height);
    }

    @Override
    public DeferredRenderingOutput render() {
        this.screen.setAsDraw();
        GlUtils.clear(GlBuffer.COLOUR);

        this.pipeline.render(this.camera, this.colourTexture, this.buffers);

        return new DeferredRenderingOutput(this.screen, this.colourTexture);
    }

    @Override
    public void delete() {
        this.screen.delete();
        this.pipeline.delete();
    }
}

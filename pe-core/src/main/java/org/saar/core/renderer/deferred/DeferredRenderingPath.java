package org.saar.core.renderer.deferred;

import org.saar.core.renderer.Renderer;
import org.saar.core.renderer.RenderersHelper;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ColourScreenImageBase;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.TextureTarget;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class DeferredRenderingPath implements RenderingPath {

    private final DeferredScreenPrototype prototype = new DeferredScreenPrototype() {

        private final Texture colourTexture = Texture.create(TextureTarget.TEXTURE_2D);

        @ScreenImageProperty(draw = true, read = true)
        private final ScreenImage colourImage = new ColourScreenImageBase(ColourAttachment.withTexture(
                0, colourTexture, FormatType.RGBA8, FormatType.RGBA, DataType.U_BYTE));

        @Override
        public ReadOnlyTexture getColourTexture() {
            return this.colourTexture;
        }
    };

    private final OffScreen screen;
    private final ReadOnlyTexture output;
    private final OffScreen passesScreen;

    private RenderersHelper renderersHelper = RenderersHelper.empty();

    private final DeferredRenderingPipeline pipeline = new DeferredRenderingPipeline();

    public DeferredRenderingPath(DeferredScreenPrototype screen, DeferredRenderer... renderers) {
        this.screen = Screens.fromPrototype(screen, fbo());
        this.output = screen.getColourTexture();
        this.passesScreen = Screens.fromPrototype(prototype, fbo());
        for (DeferredRenderer renderer : renderers) {
            addRenderer(renderer);
        }
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

    @Override
    public void render() {
        final int width = MainScreen.getInstance().getWidth();
        final int height = MainScreen.getInstance().getHeight();
        if (this.screen.getWidth() != width || this.screen.getHeight() != height) {
            this.screen.resize(width, height);
            this.passesScreen.resize(width, height);
        }

        doFirstPass();
        doRenderPasses();

        this.passesScreen.copyTo(MainScreen.getInstance());
    }

    private void doFirstPass() {
        this.screen.setAsDraw();
        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);
        this.renderersHelper.render();
    }

    private void doRenderPasses() {
        this.passesScreen.setAsDraw();
        GlUtils.clear(GlBuffer.COLOUR);
        this.pipeline.render(this.output);
    }

    @Override
    public void delete() {
        this.screen.delete();
        this.renderersHelper.delete();
        this.passesScreen.delete();
        this.pipeline.delete();
    }
}

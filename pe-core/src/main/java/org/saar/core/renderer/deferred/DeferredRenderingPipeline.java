package org.saar.core.renderer.deferred;

import org.saar.core.screen.MainScreen;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.SimpleScreen;
import org.saar.core.screen.image.ColourScreenImage;
import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class DeferredRenderingPipeline {

    private final OffScreen screen;
    private final OffScreen output;
    private RenderPassesHelper helper = RenderPassesHelper.empty();

    public DeferredRenderingPipeline(OffScreen output) {
        this.output = output;
        this.screen = screen();
    }

    private static OffScreen screen() {
        final int width = MainScreen.getInstance().getWidth();
        final int height = MainScreen.getInstance().getHeight();

        final SimpleScreen screen = new SimpleScreen(Fbo.create(width, height));
        final ColourAttachment attachment = ColourAttachment.withRenderBuffer(
                0, RenderBuffer.create(), ColourFormatType.RGBA8);
        final ColourScreenImage image = new ColourScreenImage(attachment);

        screen.addScreenImage(image);
        screen.setDrawImages(image);
        screen.setReadImages(image);
        return screen;
    }

    public void addRenderPass(RenderPass renderPass) {
        this.helper = this.helper.addRenderPass(renderPass);
    }

    public void removeRenderPass(RenderPass renderPass) {
        this.helper = this.helper.removeRenderPass(renderPass);
    }

    public void resize(int width, int height) {
        this.screen.resize(width, height);
    }

    public void render(DeferredRenderingBuffers buffers) {
        this.screen.setAsDraw();
        GlUtils.clear(GlBuffer.COLOUR);
        this.helper.render(this.screen, this.output, buffers);
    }

    public void delete() {
        this.screen.delete();
        this.helper.delete();
    }

}

package org.saar.core.screen;

import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbo.IFbo;
import org.saar.lwjgl.opengl.fbo.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbo.rendertarget.*;

import java.util.ArrayList;
import java.util.List;

public class SimpleScreen extends ScreenBase implements OffScreen {

    private final IFbo fbo;

    private final List<ScreenImage> images = new ArrayList<>();

    public SimpleScreen(IFbo fbo) {
        this.fbo = fbo;
    }

    public void addScreenImage(ScreenImage image) {
        getFbo().addAttachment(image.getAttachment());
        getScreenImages().add(image);
    }

    public void setDrawImages(ScreenImage... images) {
        final SingleRenderTarget[] targets = Screens.toRenderTargets(images);
        final DrawRenderTarget target = new DrawRenderTargetComposite(targets);
        getFbo().setDrawTarget(target);
    }

    public void setReadImages(ColourScreenImage image) {
        final ColourAttachment attachment = image.getAttachment();
        final ReadRenderTarget target = new AttachmentRenderTarget(attachment);
        getFbo().setReadTarget(target);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void delete() {
        super.delete();
    }

    @Override
    protected IFbo getFbo() {
        return this.fbo;
    }

    @Override
    protected List<ScreenImage> getScreenImages() {
        return this.images;
    }
}

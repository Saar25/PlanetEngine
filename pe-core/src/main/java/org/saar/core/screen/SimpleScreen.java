package org.saar.core.screen;

import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbos.IFbo;
import org.saar.lwjgl.opengl.fbos.attachment.Attachment;

import java.util.ArrayList;
import java.util.List;

public class SimpleScreen extends ScreenBase implements OffScreen {

    private final IFbo fbo;

    private final List<ScreenImage> images = new ArrayList<>();

    public SimpleScreen(IFbo fbo) {
        this.fbo = fbo;
    }

    public void addScreenImage(ScreenImage image) {
        image.getAttachment().init(this.fbo);
        getScreenImages().add(image);
    }

    public void setDrawImages(ScreenImage... images) {
        final Attachment[] attachment = Screens.toAttachments(images);
        getFbo().setDrawAttachments(attachment);
    }

    public void setReadImages(ColourScreenImage image) {
        getFbo().setReadAttachment(image.getAttachment());
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

package org.saar.core.screen;

import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbos.IFbo;

import java.util.List;

public class ScreenPrototypeWrapper extends ScreenBase implements OffScreen {

    private final IFbo fbo;
    private final List<ScreenImage> attachments;
    private final List<ScreenImage> readAttachments;
    private final List<ScreenImage> drawAttachments;

    public ScreenPrototypeWrapper(IFbo fbo, List<ScreenImage> attachments,
                                  List<ScreenImage> readAttachments,
                                  List<ScreenImage> drawAttachments) {
        this.fbo = fbo;
        this.attachments = attachments;
        this.readAttachments = readAttachments;
        this.drawAttachments = drawAttachments;
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
        return this.attachments;
    }

    @Override
    protected List<ScreenImage> getReadScreenImages() {
        return this.readAttachments;
    }

    @Override
    protected List<ScreenImage> getDrawScreenImages() {
        return this.drawAttachments;
    }
}

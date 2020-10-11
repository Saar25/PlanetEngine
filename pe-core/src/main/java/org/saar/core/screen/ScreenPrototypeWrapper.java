package org.saar.core.screen;

import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbos.IFbo;

import java.util.List;

public class ScreenPrototypeWrapper extends ScreenBase implements OffScreen {

    private final IFbo fbo;
    private final List<ScreenImage> attachments;

    public ScreenPrototypeWrapper(IFbo fbo, List<ScreenImage> attachments) {
        this.fbo = fbo;
        this.attachments = attachments;
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
}

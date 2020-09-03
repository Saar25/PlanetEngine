package org.saar.core.screen;

import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbos.IFbo;

import java.util.List;

public class ScreenPrototypeWrapper extends ScreenBase implements Screen {

    private final IFbo fbo;
    private final List<ScreenImage> attachments;

    public ScreenPrototypeWrapper(IFbo fbo, List<ScreenImage> attachments) {
        this.fbo = fbo;
        this.attachments = attachments;
    }

    @Override
    public IFbo getFbo() {
        return this.fbo;
    }

    @Override
    public List<ScreenImage> getScreenImages() {
        return this.attachments;
    }
}

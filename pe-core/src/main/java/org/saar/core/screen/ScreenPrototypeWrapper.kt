package org.saar.core.screen;

import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbo.IFbo;
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;

import java.util.Map;

public class ScreenPrototypeWrapper extends ScreenBase implements OffScreen {

    private final IFbo fbo;
    private final Map<AttachmentIndex, ScreenImage> images;

    public ScreenPrototypeWrapper(IFbo fbo, Map<AttachmentIndex, ScreenImage> images) {
        this.fbo = fbo;
        this.images = images;
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
    protected Map<AttachmentIndex, ScreenImage> getScreenImages() {
        return this.images;
    }
}

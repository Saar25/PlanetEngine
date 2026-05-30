package org.saar.core.screen;

import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbo.IFbo;
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;
import org.saar.lwjgl.opengl.fbo.rendertarget.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SimpleScreen extends ScreenBase implements OffScreen {

    private final IFbo fbo;

    private final Map<AttachmentIndex, ScreenImage> images = new HashMap<>();

    public SimpleScreen(IFbo fbo) {
        this.fbo = fbo;
    }

    public void addScreenImage(AttachmentIndex index, ScreenImage image) {
        getFbo().addAttachment(index, image.getAttachment());
        getScreenImages().put(index, image);
    }

    public void setDrawImages(AttachmentIndex... indices) {
        final SingleRenderTarget[] targets = Arrays.stream(indices)
                .map(IndexRenderTarget::new)
                .toArray(SingleRenderTarget[]::new);
        final DrawRenderTarget target = new DrawRenderTargetComposite(targets);
        getFbo().setDrawTarget(target);
    }

    public void setReadImages(AttachmentIndex index) {
        final ReadRenderTarget target = new IndexRenderTarget(index);
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
    protected Map<AttachmentIndex, ScreenImage> getScreenImages() {
        return this.images;
    }
}

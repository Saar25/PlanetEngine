package org.saar.core.screen;

import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbos.FboBlitFilter;
import org.saar.lwjgl.opengl.fbos.IFbo;
import org.saar.lwjgl.opengl.utils.GlBuffer;

import java.util.List;

public abstract class ScreenBase implements Screen {

    protected void init() {

    }

    @Override
    public int getWidth() {
        return getFbo().getWidth();
    }

    @Override
    public int getHeight() {
        return getFbo().getHeight();
    }

    @Override
    public void copyTo(Screen other, FboBlitFilter filter, GlBuffer... buffers) {
        setAsRead();
        other.setAsDraw();
        getFbo().blitFramebuffer(0, 0, getWidth(), getHeight(), 0, 0,
                other.getWidth(), other.getHeight(), filter, buffers);
    }

    @Override
    public void setAsDraw() {
        getFbo().bindAsDraw();
    }

    @Override
    public void setAsRead() {
        getFbo().bindAsRead();
    }

    protected void resize(int width, int height) {
        getFbo().bind();
        getFbo().resize(width, height);
        for (ScreenImage screenImage : getScreenImages()) {
            screenImage.init(getFbo());
        }
    }

    protected void delete() {
        getFbo().delete();
        for (ScreenImage screenImage : getScreenImages()) {
            screenImage.delete();
        }
    }

    protected abstract IFbo getFbo();

    protected abstract List<ScreenImage> getScreenImages();
}

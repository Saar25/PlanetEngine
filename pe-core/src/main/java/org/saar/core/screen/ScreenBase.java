package org.saar.core.screen;

import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbos.IFbo;

import java.util.List;

public abstract class ScreenBase implements Screen {

    @Override
    public void copyTo(Screen other) {
        setAsRead();
        other.setAsDraw();
        getFbo().blitFbo(other.getFbo());
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

    public abstract IFbo getFbo();

    protected abstract List<ScreenImage> getScreenImages();

    protected abstract List<ScreenImage> getReadScreenImages();

    protected abstract List<ScreenImage> getDrawScreenImages();
}

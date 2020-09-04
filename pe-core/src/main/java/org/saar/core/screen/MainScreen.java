package org.saar.core.screen;

import org.saar.lwjgl.opengl.fbos.FboBlitFilter;
import org.saar.lwjgl.opengl.fbos.ScreenFbo;
import org.saar.lwjgl.opengl.utils.GlBuffer;

public final class MainScreen implements Screen {

    private static final MainScreen instance = new MainScreen();

    private MainScreen() {

    }

    public static MainScreen getInstance() {
        return MainScreen.instance;
    }

    private ScreenFbo getFbo() {
        return ScreenFbo.getInstance();
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
    public void copyTo(Screen other) {
        setAsRead();
        other.setAsDraw();
        getFbo().blitFramebuffer(0, 0, getWidth(), getHeight(), 0, 0, other.getWidth(),
                other.getHeight(), FboBlitFilter.LINEAR, GlBuffer.COLOUR);
    }

    @Override
    public void setAsDraw() {
        getFbo().bindAsDraw();
    }

    @Override
    public void setAsRead() {
        getFbo().bindAsRead();
    }
}

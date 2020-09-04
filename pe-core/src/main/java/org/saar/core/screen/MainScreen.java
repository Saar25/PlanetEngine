package org.saar.core.screen;

import org.saar.lwjgl.opengl.fbos.IFbo;
import org.saar.lwjgl.opengl.fbos.ScreenFbo;

public final class MainScreen implements Screen {

    private static final MainScreen instance = new MainScreen();

    private MainScreen() {

    }

    public static MainScreen getInstance() {
        return MainScreen.instance;
    }

    @Override
    public IFbo getFbo() {
        return ScreenFbo.getInstance();
    }

    @Override
    public void copyTo(Screen other) {
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
}

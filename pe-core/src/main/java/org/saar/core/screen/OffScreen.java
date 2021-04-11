package org.saar.core.screen;

public interface OffScreen extends Screen {

    void resize(int width, int height);

    default void assureSize(int width, int height) {
        if (width != getWidth() || height != getHeight()) {
            resize(width, height);
        }
    }

    default void resizeToMainScreen() {
        final int width = MainScreen.getInstance().getWidth();
        final int height = MainScreen.getInstance().getHeight();
        assureSize(width, height);
    }

    void delete();

}

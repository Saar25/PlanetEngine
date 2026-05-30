package org.saar.core.screen;

public interface OffScreen extends Screen {

    void resize(int width, int height);

    default void assureSize(int width, int height) {
        if (width != getWidth() || height != getHeight()) {
            resize(width, height);
        }
    }

    default void resizeToMainScreen() {
        final int width = MainScreen.INSTANCE.getWidth();
        final int height = MainScreen.INSTANCE.getHeight();
        assureSize(width, height);
    }

    void delete();

}

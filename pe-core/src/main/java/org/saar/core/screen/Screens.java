package org.saar.core.screen;

import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbos.Fbo;

import java.util.List;

public final class Screens {

    private Screens() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static OffScreen fromPrototype(ScreenPrototype prototype, int width, int height) {
        final Fbo fbo = Fbo.create(width, height);
        final ScreenImagesLocator locator = new ScreenImagesLocator(prototype);
        final List<ScreenImage> screenImages = locator.getScreenImages();
        return new ScreenPrototypeWrapper(fbo, screenImages);
    }
}

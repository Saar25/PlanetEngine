package org.saar.core.screen;

import org.saar.core.screen.image.ScreenImage;

import java.util.List;

public final class Screens {

    private Screens() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static Screen fromPrototype(ScreenPrototype prototype) {
        final List<ScreenImage> screenImages = locateAttachments(prototype);
        return new ScreenPrototypeWrapper(prototype.getFbo(), screenImages);
    }

    private static List<ScreenImage> locateAttachments(ScreenPrototype prototype) {
        return new ScreenImagesLocator(prototype).getScreenImages();
    }
}

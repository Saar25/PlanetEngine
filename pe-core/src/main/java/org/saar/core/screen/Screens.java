package org.saar.core.screen;

import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbos.IFbo;

import java.util.List;

public final class Screens {

    private Screens() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static Screen fromPrototype(IFbo fbo, ScreenPrototype prototype) {
        final List<ScreenImage> screenImages = locateAttachments(prototype);
        return new ScreenPrototypeWrapper(fbo, screenImages);
    }

    private static List<ScreenImage> locateAttachments(ScreenPrototype prototype) {
        return new ScreenImagesLocator(prototype).getScreenImages();
    }
}

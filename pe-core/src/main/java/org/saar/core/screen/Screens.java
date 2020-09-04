package org.saar.core.screen;

import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.fbos.attachment.colour.IColourAttachment;

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
        Screens.addAttachments(fbo, screenImages);
        Screens.addDrawAttachments(fbo, locator);
        Screens.addReadAttachments(fbo, locator);
        return new ScreenPrototypeWrapper(fbo, screenImages);
    }

    private static void addAttachments(Fbo fbo, List<ScreenImage> screenImages) {
        for (ScreenImage image : screenImages) {
            fbo.addAttachment(image.getAttachment());
        }
    }

    private static void addDrawAttachments(Fbo fbo, ScreenImagesLocator locator) {
        fbo.setDrawAttachments(toColourAttachments(locator.getDrawScreenImage()));
    }

    private static void addReadAttachments(Fbo fbo, ScreenImagesLocator locator) {
        System.out.println(locator.getReadScreenImages());
        final List<ColourScreenImage> readScreenImages = locator.getReadScreenImages();
        if (readScreenImages.size() > 0) {
            fbo.setReadAttachment(readScreenImages.get(0).getAttachment());
        }
    }

    private static IColourAttachment[] toColourAttachments(List<ColourScreenImage> images) {
        return images.stream().map(ColourScreenImage::getAttachment).toArray(IColourAttachment[]::new);
    }
}

package org.saar.core.screen;

import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbos.IFbo;
import org.saar.lwjgl.opengl.fbos.ModifiableFbo;
import org.saar.lwjgl.opengl.fbos.attachment.Attachment;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class Screens {

    private Screens() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static OffScreen fromPrototype(ScreenPrototype prototype, IFbo fbo) {
        final ScreenImagesLocator locator = new ScreenImagesLocator(prototype);
        final List<ScreenImage> screenImages = locator.getScreenImages();
        Screens.addAttachments(fbo, screenImages);
        Screens.setDrawAttachments(fbo, locator);
        Screens.setReadAttachments(fbo, locator);
        return new ScreenPrototypeWrapper(fbo, screenImages);
    }

    public static Attachment[] toAttachments(ScreenImage... images) {
        return Arrays.stream(images).map(ScreenImage::getAttachment).toArray(Attachment[]::new);
    }

    private static void addAttachments(ModifiableFbo fbo, List<ScreenImage> screenImages) {
        for (ScreenImage image : screenImages) {
            fbo.addAttachment(image.getAttachment());
        }
    }

    private static void setDrawAttachments(ModifiableFbo fbo, ScreenImagesLocator locator) {
        fbo.setDrawAttachments(toColourAttachments(locator.getDrawScreenImage()));
    }

    private static void setReadAttachments(ModifiableFbo fbo, ScreenImagesLocator locator) {
        final List<ColourScreenImage> readScreenImages = locator.getReadScreenImages();
        if (readScreenImages.size() > 0) {
            fbo.setReadAttachment(readScreenImages.get(0).getAttachment());
        }
    }

    private static ColourAttachment[] toColourAttachments(List<ColourScreenImage> images) {
        return images.stream().map(ColourScreenImage::getAttachment)
                .sorted(Comparator.comparingInt(ColourAttachment::getAttachmentPoint))
                .toArray(ColourAttachment[]::new);
    }
}

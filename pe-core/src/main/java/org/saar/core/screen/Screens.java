package org.saar.core.screen;

import org.saar.core.screen.exceptions.MissingDrawAttachmentsException;
import org.saar.core.screen.exceptions.MissingReadAttachmentException;
import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbo.IFbo;
import org.saar.lwjgl.opengl.fbo.ModifiableFbo;
import org.saar.lwjgl.opengl.fbo.attachment.Attachment;
import org.saar.lwjgl.opengl.fbo.rendertarget.*;

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

        if (locator.getColourScreenImages().size() > 0) {
            Screens.setDrawAttachments(fbo, locator);
            Screens.setReadAttachments(fbo, locator);
        }

        return new ScreenPrototypeWrapper(fbo, screenImages);
    }

    public static SingleRenderTarget[] toRenderTargets(ScreenImage... images) {
        return Arrays.stream(images)
                .map(ScreenImage::getAttachment)
                .map(Attachment::getIndex)
                .map(IndexRenderTarget::new)
                .toArray(SingleRenderTarget[]::new);
    }

    private static void addAttachments(ModifiableFbo fbo, List<ScreenImage> screenImages) {
        for (ScreenImage image : screenImages) {
            fbo.addAttachment(image.getAttachment());
        }
    }

    private static void setDrawAttachments(ModifiableFbo fbo, ScreenImagesLocator locator) {
        final List<ColourScreenImage> drawScreenImages = locator.getDrawScreenImage();

        if (drawScreenImages.size() == 0) {
            throw new MissingDrawAttachmentsException("No draw attachments found in screen prototype");
        }

        final SingleRenderTarget[] renderTargets = drawScreenImages.stream()
                .map(ColourScreenImage::getAttachment)
                .sorted(Comparator.comparingInt(a -> a.getIndex().getValue()))
                .map(IndexRenderTarget::new)
                .toArray(SingleRenderTarget[]::new);
        final DrawRenderTarget target = new DrawRenderTargetComposite(renderTargets);
        fbo.setDrawTarget(target);
    }

    private static void setReadAttachments(ModifiableFbo fbo, ScreenImagesLocator locator) {
        final List<ColourScreenImage> readScreenImages = locator.getReadScreenImages();

        if (readScreenImages.size() == 0) {
            throw new MissingReadAttachmentException("No read attachment found in screen prototype");
        }

        final Attachment attachment = readScreenImages.get(0).getAttachment();
        final ReadRenderTarget target = new IndexRenderTarget(attachment.getIndex());
        fbo.setReadTarget(target);
    }
}

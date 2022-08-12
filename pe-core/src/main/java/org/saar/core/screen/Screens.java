package org.saar.core.screen;

import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbo.IFbo;
import org.saar.lwjgl.opengl.fbo.ModifiableFbo;
import org.saar.lwjgl.opengl.fbo.attachment.Attachment;
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;
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
        Screens.setDrawAttachments(fbo, locator);
        Screens.setReadAttachments(fbo, locator);

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
        final List<ScreenImage> drawScreenImages = locator.getDrawScreenImage();

        if (drawScreenImages.size() > 0) {
            final SingleRenderTarget[] renderTargets = drawScreenImages.stream()
                    .map(ScreenImage::getAttachment)
                    .map(Attachment::getIndex)
                    .sorted(Comparator.comparingInt(AttachmentIndex::getValue))
                    .map(IndexRenderTarget::new)
                    .toArray(SingleRenderTarget[]::new);
            final DrawRenderTarget target = new DrawRenderTargetComposite(renderTargets);
            fbo.setDrawTarget(target);
        }
    }

    private static void setReadAttachments(ModifiableFbo fbo, ScreenImagesLocator locator) {
        final List<ScreenImage> readScreenImages = locator.getReadScreenImages();

        if (readScreenImages.size() > 0) {
            final Attachment attachment = readScreenImages.get(0).getAttachment();
            final ReadRenderTarget target = new IndexRenderTarget(attachment.getIndex());
            fbo.setReadTarget(target);
        }
    }
}

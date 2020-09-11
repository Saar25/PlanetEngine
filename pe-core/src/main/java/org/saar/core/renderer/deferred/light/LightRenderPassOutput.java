package org.saar.core.renderer.deferred.light;

import org.saar.core.renderer.deferred.RenderPassOutput;
import org.saar.core.screen.Screen;
import org.saar.core.screen.ScreenPrototypeWrapper;
import org.saar.core.screen.image.ScreenImage;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.fbos.ScreenFbo;

import java.util.Collections;

public class LightRenderPassOutput implements RenderPassOutput {

    protected final ScreenImage outputImage;

    protected final Screen screen;

    public LightRenderPassOutput(ScreenImage outputImage) {
        this.outputImage = outputImage;
        this.screen = toScreen(outputImage);
    }

    private static Screen toScreen(ScreenImage outputImage) {
        final int width = ScreenFbo.getInstance().getWidth();
        final int height = ScreenFbo.getInstance().getHeight();
        final Fbo fbo = Fbo.create(width, height);
        fbo.setDrawAttachments(outputImage.getAttachment());
        return new ScreenPrototypeWrapper(fbo, Collections.singletonList(outputImage));
    }
}

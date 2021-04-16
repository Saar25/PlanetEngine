package org.saar.example.gui;

import org.saar.core.postprocessing.PostProcessingPipeline;
import org.saar.core.postprocessing.processors.FxaaPostProcessor;
import org.saar.core.renderer.RenderersGroup;
import org.saar.core.renderer.forward.ForwardRenderingPath;
import org.saar.core.renderer.forward.ForwardScreenPrototype;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.gui.UIComponent;
import org.saar.gui.UIDisplay;
import org.saar.gui.render.UIRenderer;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.lwjgl.opengl.textures.Texture;

public class GuiExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final UIDisplay display = new UIDisplay(window);

        final UIComponent uiComponent = new MyUIComponent();
        display.add(uiComponent);

        final UIRenderer renderer = new UIRenderer(display);

        final ForwardRenderingPath renderingPath = new ForwardRenderingPath(new ForwardScreenPrototype() {
            private final Texture colourTexture = Texture.create();

            @ScreenImageProperty
            private final ScreenImage colourImage = new ColourScreenImage(ColourAttachment.withTexture(
                    0, this.colourTexture, ColourFormatType.RGB16, FormatType.RGB, DataType.U_BYTE));

            @Override
            public ReadOnlyTexture getColourTexture() {
                return this.colourTexture;
            }
        }, null, new RenderersGroup(renderer));

        final PostProcessingPipeline fxaaPipeline = new PostProcessingPipeline(
                new FxaaPostProcessor()
        );

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            if (keyboard.isKeyPressed('R')) {
                renderingPath.render().toMainScreen();
            } else {
                final ReadOnlyTexture texture =
                        renderingPath.render().toTexture();
                fxaaPipeline.process(texture).toMainScreen();
            }

            window.update(true);
            window.pollEvents();
        }

        renderer.delete();
        window.destroy();
    }

}

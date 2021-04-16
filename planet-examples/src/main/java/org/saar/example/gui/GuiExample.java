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
import org.saar.gui.component.UIButton;
import org.saar.gui.component.UICheckbox;
import org.saar.gui.component.UISlider;
import org.saar.gui.render.UIRenderer;
import org.saar.gui.style.Colour;
import org.saar.gui.style.value.CoordinateValues;
import org.saar.gui.style.value.LengthValues;
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

        final UIButton uiButton = new UIButton();
        uiButton.getStyle().getX().set(CoordinateValues.center());
        uiButton.getStyle().getY().set(CoordinateValues.center());
        uiButton.getStyle().getWidth().set(LengthValues.percent(10));
        uiButton.getStyle().getHeight().set(LengthValues.ratio(.5f));
        uiButton.setOnAction(e -> System.out.println("Clicked!"));
        display.add(uiButton);

        final UISlider uiSlider = new UISlider();
        uiSlider.getStyle().getY().set(20);
        uiSlider.getStyle().getX().set(CoordinateValues.center());
        uiSlider.getStyle().getWidth().set(LengthValues.percent(90));
        uiSlider.getStyle().getHeight().set(20);

        uiSlider.dynamicValueProperty().addListener(e -> {
            final float percents = e.getNewValue().floatValue() / 2;
            uiButton.getStyle().getWidth().set(LengthValues.percent(percents));
        });

        display.add(uiSlider);

        final UICheckbox uiCheckbox = new UICheckbox();
        uiCheckbox.getStyle().getY().set(50);
        uiCheckbox.getStyle().getX().set(CoordinateValues.percentCenter(20));
        uiCheckbox.getStyle().getWidth().set(LengthValues.pixels(20));
        uiCheckbox.getStyle().getBackgroundColour().set(new Colour(48, 63, 159, 1f));
        uiCheckbox.getStyle().getRadiuses().set(3);
        display.add(uiCheckbox);

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

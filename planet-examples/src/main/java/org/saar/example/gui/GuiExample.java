package org.saar.example.gui;

import org.saar.core.postprocessing.PostProcessingPipeline;
import org.saar.core.postprocessing.processors.FxaaPostProcessor;
import org.saar.core.renderer.forward.ForwardRenderingPath;
import org.saar.gui.UIComponent;
import org.saar.gui.UIDisplay;
import org.saar.gui.UIGroup;
import org.saar.gui.component.UIButton;
import org.saar.gui.component.UICheckbox;
import org.saar.gui.component.UISlider;
import org.saar.gui.style.Colour;
import org.saar.gui.style.value.CoordinateValues;
import org.saar.gui.style.value.LengthValues;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public class GuiExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final UIDisplay display = new UIDisplay(window);

        final UIGroup uiGroup = new UIGroup(display);

        final UISlider sizeUiSlider = new UISlider();
        sizeUiSlider.getStyle().getY().set(450);
        sizeUiSlider.getStyle().getX().set(CoordinateValues.center());
        sizeUiSlider.getStyle().getWidth().set(LengthValues.percent(90));
        sizeUiSlider.getStyle().getHeight().set(20);

        sizeUiSlider.dynamicValueProperty().addListener(e -> {
            final float percents = (e.getNewValue().floatValue() / 100) * 50 + 20;
            uiGroup.getStyle().getWidth().set(LengthValues.percent(percents));
        });

        display.add(sizeUiSlider);


        uiGroup.getStyle().getWidth().set(LengthValues.percent(50));
        uiGroup.getStyle().getHeight().set(LengthValues.ratio(1));
        uiGroup.getStyle().getX().set(CoordinateValues.center());
        display.add(uiGroup);

        final UIComponent uiComponent = new MyUIComponent();
        uiGroup.add(uiComponent);

        final UIButton uiButton = new UIButton();
        uiButton.getStyle().getX().set(CoordinateValues.center());
        uiButton.getStyle().getY().set(CoordinateValues.center());
        uiButton.getStyle().getWidth().set(LengthValues.percent(10));
        uiButton.getStyle().getHeight().set(LengthValues.ratio(.5f));
        uiButton.setOnAction(e -> System.out.println("Clicked!"));
        uiGroup.add(uiButton);

        final UISlider uiSlider = new UISlider();
        uiSlider.getStyle().getY().set(20);
        uiSlider.getStyle().getX().set(CoordinateValues.center());
        uiSlider.getStyle().getWidth().set(LengthValues.percent(90));
        uiSlider.getStyle().getHeight().set(20);

        uiSlider.dynamicValueProperty().addListener(e -> {
            final float percents = e.getNewValue().floatValue() / 2;
            uiButton.getStyle().getWidth().set(LengthValues.percent(percents));
        });

        uiGroup.add(uiSlider);

        final UICheckbox uiCheckbox = new UICheckbox();
        uiCheckbox.getStyle().getY().set(50);
        uiCheckbox.getStyle().getX().set(CoordinateValues.percentCenter(20));
        uiCheckbox.getStyle().getWidth().set(LengthValues.pixels(20));
        uiCheckbox.getStyle().getBackgroundColour().set(new Colour(48, 63, 159, 1f));
        uiCheckbox.getStyle().getRadiuses().set(3);
        uiGroup.add(uiCheckbox);

        final ForwardRenderingPath renderingPath = new ForwardRenderingPath(null, display);

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

        renderingPath.delete();
        display.delete();
        window.destroy();
    }

}

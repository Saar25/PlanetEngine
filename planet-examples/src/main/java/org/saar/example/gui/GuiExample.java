package org.saar.example.gui;

import org.saar.core.renderer.RenderContextBase;
import org.saar.gui.UIComponent;
import org.saar.gui.UIContainer;
import org.saar.gui.UIDisplay;
import org.saar.gui.component.UIButton;
import org.saar.gui.component.UICheckbox;
import org.saar.gui.component.UISlider;
import org.saar.gui.style.Colour;
import org.saar.gui.style.value.CoordinateValues;
import org.saar.gui.style.value.LengthValues;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class GuiExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final UIDisplay display = new UIDisplay(window);

        final UIContainer uiContainer = new UIContainer();

        final UISlider sizeUiSlider = new UISlider();
        sizeUiSlider.getStyle().getY().set(450);
        sizeUiSlider.getStyle().getX().set(CoordinateValues.center());
        sizeUiSlider.getStyle().getWidth().set(LengthValues.percent(90));
        sizeUiSlider.getStyle().getHeight().set(20);

        sizeUiSlider.getDynamicValueProperty().addListener(e -> {
            final float percents = (e.getNewValue().floatValue() / 100) * 50 + 20;
            uiContainer.getStyle().getWidth().set(LengthValues.percent(percents));
        });

        display.add(sizeUiSlider);


        uiContainer.getStyle().getWidth().set(LengthValues.percent(50));
        uiContainer.getStyle().getHeight().set(LengthValues.ratio(1));
        uiContainer.getStyle().getX().set(CoordinateValues.center());
        display.add(uiContainer);

        final UIComponent uiComponent = new MyUIComponent();
        uiContainer.add(uiComponent);

        final UIButton uiButton = new UIButton();
        uiButton.getStyle().getX().set(CoordinateValues.center());
        uiButton.getStyle().getY().set(CoordinateValues.center());
        uiButton.getStyle().getWidth().set(LengthValues.percent(10));
        uiButton.getStyle().getHeight().set(LengthValues.ratio(.5f));
        uiButton.setOnAction(e -> System.out.println("Clicked!"));
        uiContainer.add(uiButton);

        final UISlider uiSlider = new UISlider();
        uiSlider.getStyle().getY().set(20);
        uiSlider.getStyle().getX().set(CoordinateValues.center());
        uiSlider.getStyle().getWidth().set(LengthValues.percent(90));
        uiSlider.getStyle().getHeight().set(20);

        uiSlider.getDynamicValueProperty().addListener(e -> {
            final float percents = e.getNewValue().floatValue() / 2;
            uiButton.getStyle().getWidth().set(LengthValues.percent(percents));
        });

        uiContainer.add(uiSlider);

        final UICheckbox uiCheckbox = new UICheckbox();
        uiCheckbox.getStyle().getY().set(50);
        uiCheckbox.getStyle().getX().set(CoordinateValues.percentCenter(20));
        uiCheckbox.getStyle().getWidth().set(LengthValues.pixels(20));
        uiCheckbox.getStyle().getBackgroundColour().set(new Colour(48, 63, 159, 1f));
        uiCheckbox.getStyle().getRadiuses().set(3);
        uiContainer.add(uiCheckbox);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            GlUtils.clear(GlBuffer.COLOUR);
            display.render(new RenderContextBase(null));

            display.update();
            window.update(true);
            window.pollEvents();
        }

        display.delete();
        window.destroy();
    }

}

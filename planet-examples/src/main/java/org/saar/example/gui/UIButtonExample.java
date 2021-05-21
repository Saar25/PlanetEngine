package org.saar.example.gui;

import org.saar.core.renderer.RenderContextBase;
import org.saar.gui.UIDisplay;
import org.saar.gui.UIText;
import org.saar.gui.component.UIButton;
import org.saar.gui.font.Font;
import org.saar.gui.font.FontKt;
import org.saar.gui.font.FontLoader;
import org.saar.gui.style.value.CoordinateValues;
import org.saar.gui.style.value.LengthValues;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class UIButtonExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final UIDisplay display = new UIDisplay(window);

        final UIButton uiButton = new UIButton();
        uiButton.getStyle().getX().set(CoordinateValues.center());
        uiButton.getStyle().getY().set(CoordinateValues.center());
        uiButton.getStyle().getWidth().set(LengthValues.percent(50));
        uiButton.getStyle().getHeight().set(LengthValues.ratio(.5f));
        uiButton.setOnAction(e -> System.out.println("Clicked!"));
        display.add(uiButton);

        final Font font = FontLoader.loadFont("C:/Windows/Fonts/arial.ttf",
                48f, 512, 512, "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz");

        final UIText uiText = new UIText(display, font, "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz");
        uiText.getStyle().getX().set(0);
        uiText.getStyle().getY().set(0);
        display.add(uiText);

        final UIText uiText2 = new UIText(display, font, "abcdefghijklmnopqrstuvwxyz");
        uiText2.getStyle().getX().set(0);
        uiText2.getStyle().getY().set(50);
        display.add(uiText2);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            GlUtils.clearColourAndDepthBuffer();
            display.renderForward(new RenderContextBase(null));

            window.update(true);
            window.pollEvents();
        }

        FontKt.delete(font);

        display.delete();
        window.destroy();
    }

}

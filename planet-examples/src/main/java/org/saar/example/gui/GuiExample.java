package org.saar.example.gui;

import org.saar.core.renderer.RenderContextBase;
import org.saar.gui.UIComponent;
import org.saar.gui.UIDisplay;
import org.saar.gui.UIObject;
import org.saar.gui.position.coordinate.CoordinateValues;
import org.saar.gui.position.length.LengthValues;
import org.saar.gui.render.UIRenderer;
import org.saar.gui.style.property.Colours;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class GuiExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final UIDisplay display = new UIDisplay(window);
        final UIComponent uiComponent = new UIComponent();
        display.add(uiComponent);

        final UIObject object = new UIObject(uiComponent);

        object.getPositioner().getX().set(CoordinateValues.center());
        object.getPositioner().getY().set(CoordinateValues.center());
        object.getPositioner().getWidth().set(LengthValues.pixels(50));
        object.getPositioner().getHeight().set(LengthValues.pixels(50));

        object.getStyle().getBackgroundColour().set(Colours.CYAN);
        object.getStyle().getBorderColour().set(Colours.LIGHT_GREY);
        object.getStyle().getBorders().set(3);
        object.getStyle().getRadiuses().set(100);

        final UIRenderer renderer = new UIRenderer(object);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            GlUtils.clearColourAndDepthBuffer();
            renderer.render(new RenderContextBase(null));

            window.update(true);
            window.pollEvents();
        }

        object.delete();
        renderer.delete();
        window.destroy();
    }

}

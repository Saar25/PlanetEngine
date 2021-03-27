package org.saar.example.gui;

import org.saar.core.renderer.RenderContextBase;
import org.saar.gui.GuiObject;
import org.saar.gui.render.GuiRenderer;
import org.saar.gui.style.property.Colours;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class GuiExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final GuiObject object = new GuiObject();
        object.getStyle().x.set(20);
        object.getStyle().y.set(20);
        object.getStyle().width.set(500);
        object.getStyle().height.set(200);
        object.getStyle().backgroundColour.set(Colours.CYAN);
        object.getStyle().borderColour.set(Colours.LIGHT_GREY);
        object.getStyle().borders.set(3);
        object.getStyle().radiuses.set(100);

        final GuiRenderer renderer = new GuiRenderer(object);

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

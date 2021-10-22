package org.saar.example;

import org.saar.core.painting.Painter;
import org.saar.core.painting.painters.FBMPainter;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.glfw.window.WindowHints;

import java.awt.*;

public class PaintingExample {

    public static void main(String[] args) throws Exception {
        final int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        final int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        final Window window = Window.builder("Lwjgl", width / 2, height / 2, true)
                .hint(WindowHints.decorated(false))
                //                .hint(WindowHints.maximized())
                .hint(WindowHints.focused())
                .build();

        final Painter painter = new FBMPainter();

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            painter.render();
            window.swapBuffers();
            window.pollEvents();
        }

        painter.delete();
        window.destroy();
    }
}

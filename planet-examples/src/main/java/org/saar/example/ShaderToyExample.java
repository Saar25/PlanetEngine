package org.saar.example;

import org.saar.core.shadertoy.toys.RandomShaderToy;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;

public class ShaderToyExample {

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", 700, 500, true);

        final RandomShaderToy shaderToy = new RandomShaderToy();

        shaderToy.render();
        window.update(true);

        window.addResizeListener(e -> {
            window.update(false);
            shaderToy.render();
            window.update(true);
        });

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            window.pollEvents();
        }

        shaderToy.delete();
        window.destroy();
    }
}

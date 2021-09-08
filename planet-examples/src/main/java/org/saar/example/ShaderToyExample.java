package org.saar.example;

import org.saar.core.shadertoy.ShaderToy;
import org.saar.core.shadertoy.toys.FBMShaderToy;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.glfw.window.hint.WindowHintDecorated;
import org.saar.lwjgl.glfw.window.hint.WindowHintFocused;
import org.saar.lwjgl.glfw.window.hint.WindowHintMaximized;

import java.awt.*;

public class ShaderToyExample {

    public static void main(String[] args) throws Exception {
        final int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        final int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        final Window window = Window.builder("Lwjgl", width, height, true)
                .hint(new WindowHintDecorated(false))
                .hint(new WindowHintMaximized(true))
                .hint(new WindowHintFocused(true))
                .build();

        final ShaderToy shaderToy = new FBMShaderToy();

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            shaderToy.render();
            window.update(true);
            window.pollEvents();
        }

        shaderToy.delete();
        window.destroy();
    }
}

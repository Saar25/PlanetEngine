package org.saar.lwjgl.glfw.input.keyboard;

import org.saar.lwjgl.glfw.input.Event;

public class KeyPressEvent extends Event {

    private final int keyCode;

    public KeyPressEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }
}

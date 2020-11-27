package org.saar.lwjgl.glfw.input.keyboard;

import org.saar.lwjgl.glfw.event.Event;

public class KeyReleaseEvent extends Event {

    private final int keyCode;

    public KeyReleaseEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }
}

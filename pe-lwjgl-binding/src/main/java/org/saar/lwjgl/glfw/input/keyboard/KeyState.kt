package org.saar.lwjgl.glfw.input.keyboard;

import org.lwjgl.glfw.GLFW;

public enum KeyState {

    RELEASE(GLFW.GLFW_RELEASE),
    PRESS(GLFW.GLFW_PRESS),
    REPEAT(GLFW.GLFW_REPEAT),
    ;

    private static final KeyState[] values = values();

    private final int value;

    KeyState(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }

    public static KeyState valueOf(int value) {
        final int index = value - KeyState.values[0].get();
        if (index >= 0 && index <= KeyState.values.length) {
            return KeyState.values[index];
        }
        throw new IllegalArgumentException("KeyState non found: " + value);
    }
}

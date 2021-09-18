package org.saar.lwjgl.glfw.input.mouse;

import org.lwjgl.glfw.GLFW;

public enum MouseButtonState {

    RELEASE(GLFW.GLFW_RELEASE),
    PRESS(GLFW.GLFW_PRESS),
    ;

    private final int value;

    MouseButtonState(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }

    public static MouseButtonState valueOf(int value) {
        switch (value) {
            case GLFW.GLFW_RELEASE:
                return MouseButtonState.RELEASE;
            case GLFW.GLFW_PRESS:
                return MouseButtonState.PRESS;
        }
        throw new IllegalArgumentException("MouseButtonState non found: " + value);
    }
}

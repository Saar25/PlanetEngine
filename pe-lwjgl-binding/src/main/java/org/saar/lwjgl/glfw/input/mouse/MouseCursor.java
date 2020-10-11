package org.saar.lwjgl.glfw.input.mouse;

import org.lwjgl.glfw.GLFW;

public enum MouseCursor {

    NORMAL(GLFW.GLFW_CURSOR_NORMAL),
    HIDDEN(GLFW.GLFW_CURSOR_HIDDEN),
    DISABLED(GLFW.GLFW_CURSOR_DISABLED),
    ;

    private static final MouseCursor[] values = values();

    private final int value;

    MouseCursor(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }

    public static MouseCursor valueOf(int value) {
        final int index = value - MouseCursor.values[0].get();
        if (index >= 0 && index <= MouseCursor.values.length) {
            return MouseCursor.values[index];
        }
        throw new IllegalArgumentException("MouseCursor non found: " + value);
    }
}

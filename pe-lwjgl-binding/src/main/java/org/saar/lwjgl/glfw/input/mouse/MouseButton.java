package org.saar.lwjgl.glfw.input.mouse;

import org.lwjgl.glfw.GLFW;

public enum MouseButton {

    NONE(0), // PRIMARY

    BTN_1(GLFW.GLFW_MOUSE_BUTTON_1), // PRIMARY
    BTN_2(GLFW.GLFW_MOUSE_BUTTON_2), // SECONDARY
    BTN_3(GLFW.GLFW_MOUSE_BUTTON_3), // MIDDLE
    BTN_4(GLFW.GLFW_MOUSE_BUTTON_4),
    BTN_5(GLFW.GLFW_MOUSE_BUTTON_5),
    BTN_6(GLFW.GLFW_MOUSE_BUTTON_6),
    BTN_7(GLFW.GLFW_MOUSE_BUTTON_7),
    BTN_8(GLFW.GLFW_MOUSE_BUTTON_8), // LAST
    ;

    private static final MouseButton[] values = values();

    public static final MouseButton PRIMARY = BTN_1;
    public static final MouseButton SECONDARY = BTN_2;
    public static final MouseButton MIDDLE = BTN_3;
    public static final MouseButton LAST = BTN_8;

    private final int value;

    MouseButton(int value) {
        this.value = value;
    }

    public static MouseButton valueOf(int value) {
        final int index = value - MouseButton.values[0].get();
        if (index >= 0 && index <= MouseButton.values.length) {
            return MouseButton.values[index];
        }
        throw new IllegalArgumentException("MouseButton non found: " + value);
    }

    public int get() {
        return this.value;
    }

    public boolean isPrimary() {
        return this == MouseButton.PRIMARY;
    }

    public boolean isSecondary() {
        return this == MouseButton.SECONDARY;
    }

}

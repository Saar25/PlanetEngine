package org.saar.lwjgl.glfw.input.event;

import org.saar.lwjgl.glfw.input.Mouse;
import org.saar.lwjgl.glfw.input.MouseButton;

public class ClickEvent extends MouseEvent {

    private final MouseButton button;
    private final boolean isDown;

    public ClickEvent(Mouse mouse, MouseButton button, boolean isDown) {
        super(mouse);
        this.button = button;
        this.isDown = isDown;
    }

    public MouseButton getButton() {
        return button;
    }

    public boolean isDown() {
        return isDown;
    }
}

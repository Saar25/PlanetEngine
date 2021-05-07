package org.saar.lwjgl.glfw.input.mouse;

import org.saar.lwjgl.glfw.event.Event;

public class ClickEvent extends Event {

    private final Mouse mouse;

    private final MouseButton button;
    private final boolean isDown;

    public ClickEvent(Mouse mouse, MouseButton button, boolean isDown) {
        this.mouse = mouse;
        this.button = button;
        this.isDown = isDown;
    }

    public MouseButton getButton() {
        return this.button;
    }

    public boolean isDown() {
        return this.isDown;
    }

    public Mouse getMouse() {
        return this.mouse;
    }
}

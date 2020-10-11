package org.saar.lwjgl.glfw.input.mouse;

import org.saar.lwjgl.glfw.input.Event;

public class MouseEvent extends Event {

    private final Mouse mouse;

    public MouseEvent(Mouse mouse) {
        this.mouse = mouse;
    }

    public Mouse getMouse() {
        return mouse;
    }
}

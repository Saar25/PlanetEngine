package org.saar.lwjgl.glfw.input.mouse;

import org.saar.lwjgl.glfw.event.Event;

public class ScrollEvent extends Event {

    private final Mouse mouse;

    private final double offset;

    public ScrollEvent(Mouse mouse, double offset) {
        this.mouse = mouse;
        this.offset = offset;
    }

    public double getOffset() {
        return this.offset;
    }

    public Mouse getMouse() {
        return this.mouse;
    }
}

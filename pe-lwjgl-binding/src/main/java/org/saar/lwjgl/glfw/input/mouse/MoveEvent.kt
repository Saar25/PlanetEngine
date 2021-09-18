package org.saar.lwjgl.glfw.input.mouse;

import org.saar.lwjgl.glfw.event.Event;
import org.saar.lwjgl.glfw.event.IntValueChange;

public class MoveEvent extends Event {

    private final Mouse mouse;

    private final IntValueChange x;
    private final IntValueChange y;

    public MoveEvent(Mouse mouse, IntValueChange x, IntValueChange y) {
        this.mouse = mouse;
        this.x = x;
        this.y = y;
    }

    public IntValueChange getX() {
        return this.x;
    }

    public IntValueChange getY() {
        return this.y;
    }

    public Mouse getMouse() {
        return this.mouse;
    }
}

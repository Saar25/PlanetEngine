package org.saar.lwjgl.glfw.window;

import org.saar.lwjgl.glfw.event.IntValueChange;
import org.saar.lwjgl.glfw.event.Event;

public class PositionEvent extends Event {

    private final IntValueChange x;
    private final IntValueChange y;

    public PositionEvent(IntValueChange x, IntValueChange y) {
        this.x = x;
        this.y = y;
    }

    public IntValueChange getWidth() {
        return this.x;
    }

    public IntValueChange getHeight() {
        return this.y;
    }
}

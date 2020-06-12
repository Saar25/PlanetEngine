package org.saar.lwjgl.glfw.input.event;

import org.saar.lwjgl.glfw.input.Mouse;

public class ScrollEvent extends MouseEvent {

    private final double offset;

    public ScrollEvent(Mouse mouse, double offset) {
        super(mouse);
        this.offset = offset;
    }

    public double getOffset() {
        return offset;
    }
}

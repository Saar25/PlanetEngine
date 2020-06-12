package org.saar.lwjgl.glfw.input.event;

import org.saar.lwjgl.glfw.input.Mouse;

public class MoveEvent extends MouseEvent {

    private final double x;
    private final double y;

    public MoveEvent(Mouse mouse, double x, double y) {
        super(mouse);
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

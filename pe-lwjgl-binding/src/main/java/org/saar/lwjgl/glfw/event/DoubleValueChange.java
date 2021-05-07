package org.saar.lwjgl.glfw.event;

public class DoubleValueChange {

    private final double before;
    private final double after;

    public DoubleValueChange(double before, double after) {
        this.before = before;
        this.after = after;
    }

    public double getBefore() {
        return this.before;
    }

    public double getAfter() {
        return this.after;
    }

    public double getDifference() {
        return getBefore() - getAfter();
    }
}

package org.saar.lwjgl.glfw.event;

public class IntValueChange {

    private final int before;
    private final int after;

    public IntValueChange(int before, int after) {
        this.before = before;
        this.after = after;
    }

    public int getBefore() {
        return this.before;
    }

    public int getAfter() {
        return this.after;
    }

    public int getDifference() {
        return getBefore() - getAfter();
    }
}

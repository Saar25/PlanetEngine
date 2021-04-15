package org.saar.gui.style.property;

import org.joml.Vector4i;
import org.joml.Vector4ic;
import org.saar.gui.position.IPositioner;
import org.saar.gui.style.StyleProperty;

public class Radiuses implements StyleProperty {

    private final Vector4i vector = new Vector4i();

    private final IPositioner positioner;

    public int topLeft = 0;
    public int topRight = 0;
    public int bottomRight = 0;
    public int bottomLeft = 0;

    public Radiuses(IPositioner positioner) {
        this.positioner = positioner;
    }

    public int get(boolean right, boolean top) {
        if (top) return clamp(right ? this.topRight : this.topLeft);
        return clamp(right ? this.bottomRight : this.bottomLeft);
    }

    public boolean isZero() {
        return this.topRight == 0 && this.topLeft == 0 &&
                this.bottomRight == 0 && this.bottomLeft == 0;
    }

    public void set(int all) {
        set(all, all, all, all);
    }

    public void set(int top, int right, int bottom, int left) {
        this.topLeft = top;
        this.topRight = right;
        this.bottomRight = bottom;
        this.bottomLeft = left;
    }

    public void set(Radiuses borders) {
        this.topLeft = borders.topLeft;
        this.topRight = borders.topRight;
        this.bottomRight = borders.bottomRight;
        this.bottomLeft = borders.bottomLeft;
    }

    public Vector4ic asVector4i() {
        return this.vector.set(
                clamp(this.topLeft),
                clamp(this.topRight),
                clamp(this.bottomRight),
                clamp(this.bottomLeft));
    }

    private int clamp(int value) {
        final int w = this.positioner.getWidth().get();
        final int h = this.positioner.getHeight().get();
        final int max = Math.min(w, h) / 2;

        return Math.max(0, Math.min(max, value));
    }

    @Override
    public String toString() {
        return String.format("[Radiuses: top=%d, right=%d, bottom=%d, left=%d]",
                this.topLeft, this.topRight, this.bottomRight, this.bottomLeft);
    }
}

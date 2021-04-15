package org.saar.gui.style.property;

import org.joml.Vector4i;
import org.joml.Vector4ic;
import org.saar.gui.style.StyleProperty;

public class Radiuses implements StyleProperty {

    private final Vector4i vector = new Vector4i();

    public int topLeft = 0;
    public int topRight = 0;
    public int bottomRight = 0;
    public int bottomLeft = 0;

    public int get(boolean right, boolean top) {
        if (top) return right ? this.topRight : this.topLeft;
        return right ? this.bottomRight : this.bottomLeft;
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
                this.topLeft, this.topRight,
                this.bottomRight, this.bottomLeft);
    }

    @Override
    public String toString() {
        return String.format("[Radiuses: top=%d, right=%d, bottom=%d, left=%d]",
                this.topLeft, this.topRight, this.bottomRight, this.bottomLeft);
    }
}

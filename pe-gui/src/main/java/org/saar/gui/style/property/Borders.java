package org.saar.gui.style.property;

import org.joml.Vector4i;
import org.joml.Vector4ic;
import org.saar.gui.style.StyleProperty;

public class Borders implements StyleProperty {

    private final Vector4i vector = new Vector4i();

    public int top = 0;
    public int right = 0;
    public int bottom = 0;
    public int left = 0;

    public void set(int top, int right, int bottom, int left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }

    public void set(int all) {
        set(all, all, all, all);
    }

    public void set(Borders borders) {
        this.top = borders.top;
        this.right = borders.right;
        this.bottom = borders.bottom;
        this.left = borders.left;
    }

    public Vector4ic asVector4i() {
        return this.vector.set(this.top, this.right, this.bottom, this.left);
    }

    @Override
    public String toString() {
        return String.format("[Borders: top=%d, right=%d, bottom=%d, left=%d]",
                this.top, this.right, this.bottom, this.left);
    }
}

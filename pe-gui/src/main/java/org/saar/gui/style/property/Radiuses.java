package org.saar.gui.style.property;

import org.joml.Vector4i;
import org.joml.Vector4ic;
import org.saar.gui.style.Style;
import org.saar.gui.style.StyleProperty;

public class Radiuses implements StyleProperty {

    public final Length topLeft;
    public final Length topRight;
    public final Length bottomRight;
    public final Length bottomLeft;

    private final Vector4i vector = new Vector4i();

    public Radiuses(Style container) {
        this.topLeft = new Length(container);
        this.topRight = new Length(container);
        this.bottomRight = new Length(container);
        this.bottomLeft = new Length(container);
    }

    public Length get(boolean right, boolean top) {
        final Length[] values = {
                topRight, topLeft,
                bottomRight, bottomLeft
        };
        return values[(top ? 0 : 2) + (right ? 0 : 1)];
    }

    public boolean isZero() {
        return this.topRight.get() == 0 &&
                this.topLeft.get() == 0 &&
                this.bottomRight.get() == 0 &&
                this.bottomLeft.get() == 0;
    }

    public void set(int all) {
        set(all, all, all, all);
    }

    public void set(int top, int right, int bottom, int left) {
        this.topLeft.set(top);
        this.topRight.set(right);
        this.bottomRight.set(bottom);
        this.bottomLeft.set(left);
    }

    public void set(Radiuses borders) {
        this.topLeft.set(borders.topLeft);
        this.topRight.set(borders.topRight);
        this.bottomRight.set(borders.bottomRight);
        this.bottomLeft.set(borders.bottomLeft);
    }

    public Vector4ic asVector4i() {
        return this.vector.set(
                this.topLeft.get(),
                this.topRight.get(),
                this.bottomRight.get(),
                this.bottomLeft.get());
    }

    @Override
    public String toString() {
        return String.format("[Radiuses: top=%d, right=%d, bottom=%d, left=%d]",
                this.topLeft.get(), this.topRight.get(), this.bottomRight.get(), this.bottomLeft.get());
    }
}

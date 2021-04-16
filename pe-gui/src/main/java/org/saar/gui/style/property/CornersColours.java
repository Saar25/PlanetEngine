package org.saar.gui.style.property;

import org.joml.Vector4i;
import org.joml.Vector4ic;
import org.saar.gui.style.Colour;
import org.saar.gui.style.Colours;
import org.saar.gui.style.StyleProperty;

public class CornersColours implements StyleProperty {

    private final Vector4i vector = new Vector4i();

    public Colour topLeft = Colours.BLACK;
    public Colour topRight = Colours.BLACK;
    public Colour bottomRight = Colours.BLACK;
    public Colour bottomLeft = Colours.BLACK;

    public void set(Colour topLeft, Colour topRight,
                    Colour bottomRight, Colour bottomLeft) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
    }

    public void set(Colour topLeft, Colour sides, Colour bottomRight) {
        set(topLeft, sides, bottomRight, sides);
    }

    public void set(Colour topLeftBottomRight, Colour topRightBottomLeft) {
        set(topLeftBottomRight, topRightBottomLeft, topLeftBottomRight, topRightBottomLeft);
    }

    public void set(Colour a, Colour b, Orientation orientation) {
        switch (orientation) {
            case VERTICAL:
                set(a, a, b, b);
                break;
            case HORIZONTAL:
                set(a, b, b, a);
                break;
        }
    }

    public void set(Colour all) {
        set(all, all, all, all);
    }

    public void set(CornersColours corners) {
        set(corners.topLeft, corners.topRight,
                corners.bottomRight, corners.bottomLeft);
    }

    public Colour get() {
        return this.topRight;
    }

    public Colour getTopLeft() {
        return this.topLeft;
    }

    public Colour getTopRight() {
        return this.topRight;
    }

    public Colour getBottomRight() {
        return this.bottomRight;
    }

    public Colour getBottomLeft() {
        return this.bottomLeft;
    }

    public void set(int r, int g, int b, int a) {
        set(new Colour(r, g, b, a));
    }

    public Vector4ic asVector4i() {
        return this.vector.set(
                this.bottomLeft.asInt(),
                this.topLeft.asInt(),
                this.bottomRight.asInt(),
                this.topRight.asInt());
    }

    @Override
    public String toString() {
        return String.format("[Corners: topLeft=%s, topRight=%s, bottomRight=%s, bottomLeft=%s]",
                this.topLeft, this.topRight, this.bottomRight, this.bottomLeft);
    }

}

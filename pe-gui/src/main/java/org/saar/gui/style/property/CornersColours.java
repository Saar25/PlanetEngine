package org.saar.gui.style.property;

import org.joml.Vector4i;
import org.joml.Vector4ic;
import org.saar.gui.style.StyleProperty;
import org.saar.gui.style.Colour;
import org.saar.gui.style.colour.ReadonlyColour;

public class CornersColours implements StyleProperty {

    public final Colour topLeft = new Colour();
    public final Colour topRight = new Colour();
    public final Colour bottomRight = new Colour();
    public final Colour bottomLeft = new Colour();

    private final Vector4i vector = new Vector4i();

    public CornersColours() {

    }

    public CornersColours(Colour all) {
        set(all);
    }

    public CornersColours(ReadonlyColour topLeft, ReadonlyColour topRight,
                          ReadonlyColour bottomRight, ReadonlyColour bottomLeft) {
        set(topLeft, topRight, bottomRight, bottomLeft);
    }

    public void set(ReadonlyColour topLeft, ReadonlyColour topRight,
                    ReadonlyColour bottomRight, ReadonlyColour bottomLeft) {
        this.topLeft.set(topLeft);
        this.topRight.set(topRight);
        this.bottomRight.set(bottomRight);
        this.bottomLeft.set(bottomLeft);
    }

    public void set(ReadonlyColour topLeft, ReadonlyColour sides, ReadonlyColour bottomRight) {
        set(topLeft, sides, bottomRight, sides);
    }

    public void set(ReadonlyColour topLeftBottomRight, ReadonlyColour topRightBottomLeft) {
        set(topLeftBottomRight, topRightBottomLeft, topLeftBottomRight, topRightBottomLeft);
    }

    public void set(ReadonlyColour a, ReadonlyColour b, Orientation orientation) {
        switch (orientation) {
            case VERTICAL:
                set(a, a, b, b);
                break;
            case HORIZONTAL:
                set(a, b, b, a);
                break;
        }
    }

    public void set(ReadonlyColour all) {
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

    public void setPackedRGB(int rgb) {
        float r = ((rgb >> 16) & 255) / 255f;
        float g = ((rgb >> 8) & 255) / 255f;
        float b = ((rgb) & 255) / 255f;
        setNormalized(r, g, b, 1);
    }

    public void setPackedRGBA(int rgba) {
        float r = ((rgba >> 24) & 255) / 255f;
        float g = ((rgba >> 16) & 255) / 255f;
        float b = ((rgba >> 8) & 255) / 255f;
        float a = ((rgba) & 255) / 255f;
        setNormalized(r, g, b, a);
    }

    public void set(int r, int g, int b) {
        set(r, g, b, 255);
    }

    public void set(int r, int g, int b, int a) {
        this.topLeft.set(r, g, b, a);
        this.topRight.set(r, g, b, a);
        this.bottomRight.set(r, g, b, a);
        this.bottomLeft.set(r, g, b, a);
    }

    public void setNormalized(float r, float g, float b) {
        setNormalized(r, g, b, 1);
    }

    public void setNormalized(float r, float g, float b, float a) {
        this.topLeft.setNormalized(r, g, b, a);
        this.topRight.setNormalized(r, g, b, a);
        this.bottomRight.setNormalized(r, g, b, a);
        this.bottomLeft.setNormalized(r, g, b, a);
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

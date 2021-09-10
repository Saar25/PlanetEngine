package org.saar.maths.objects;

import org.joml.Vector2ic;
import org.saar.maths.utils.Maths;

public class RectangleI {

    public int x;
    public int y;
    public int w;
    public int h;

    public RectangleI(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public RectangleI(Vector2ic position, Vector2ic dimensions) {
        this.x = position.x();
        this.y = position.y();
        this.w = dimensions.x();
        this.h = dimensions.y();
    }

    public RectangleI(RectangleI rectangle) {
        this.x = rectangle.x;
        this.y = rectangle.y;
        this.w = rectangle.w;
        this.h = rectangle.h;
    }

    public static RectangleI ofPosition(int x, int y) {
        return new RectangleI(x, y, 0, 0);
    }

    public static RectangleI ofDimensions(int w, int h) {
        return new RectangleI(0, 0, w, h);
    }

    public static RectangleI fromPoints(int x1, int y1, int x2, int y2) {
        return new RectangleI(x1, y1, x2 - x1, y2 - y1);
    }

    public int centerX() {
        return x + w / 2;
    }

    public int centerY() {
        return y + h / 2;
    }

    public boolean contains(int x, int y) {
        return Maths.isBetween(x, this.x, this.x + this.w) && Maths.isBetween(y, this.y, this.y + this.h);
    }

    @Override
    public String toString() {
        return String.format("[Rectangle: x=%d, y=%d, w=%d, h=%d]", x, y, w, h);
    }
}

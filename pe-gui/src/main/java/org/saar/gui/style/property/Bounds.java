package org.saar.gui.style.property;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.joml.Vector4i;
import org.joml.Vector4ic;
import org.saar.gui.style.StyleProperty;
import org.saar.maths.utils.Maths;

public class Bounds implements StyleProperty {

    private final Position position;
    private final Dimensions dimensions;

    private final Vector2i center = new Vector2i();
    private final Vector4i vector = new Vector4i();

    public Bounds(Position position, Dimensions dimensions) {
        this.position = position;
        this.dimensions = dimensions;
    }

    public void set(Bounds bounds) {
        this.position.set(bounds.position);
        this.dimensions.set(bounds.dimensions);
    }

    public void setMiddlePosition(int x, int y) {
        setMiddleX(x);
        setMiddleY(y);
    }

    public void setMiddleX(int x) {
        this.position.x.set(x - this.dimensions.width.get() / 2);
    }

    public void setMiddleY(int y) {
        this.position.y.set(y - this.dimensions.height.get() / 2);
    }

    public int xMax() {
        return this.position.x.get() + this.dimensions.width.get();
    }

    public int yMax() {
        return this.position.y.get() + this.dimensions.height.get();
    }

    public int xMin() {
        return this.position.x.get();
    }

    public int yMin() {
        return this.position.y.get();
    }

    public int w() {
        return this.dimensions.width.get();
    }

    public int h() {
        return this.dimensions.height.get();
    }

    public int xCenter() {
        return this.position.x.get() + this.dimensions.width.get() / 2;
    }

    public int yCenter() {
        return this.position.y.get() + this.dimensions.height.get() / 2;
    }

    public Vector2ic getCenter() {
        return this.center.set(xCenter(), yCenter());
    }

    public boolean contains(float x, float y) {
        return Maths.isBetween(x, xMin(), xMax()) &&
                Maths.isBetween(y, yMin(), yMax());
    }

    public Vector4ic asVector4i() {
        return this.vector.set(xMin(), yMin(), w(), h());
    }

    @Override
    public String toString() {
        return String.format("[Rectangle: x=%d, y=%d, w=%d, h=%d]",
                xMin(), yMin(), w(), h());
    }

}

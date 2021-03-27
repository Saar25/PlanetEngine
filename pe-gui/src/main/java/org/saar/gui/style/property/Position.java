package org.saar.gui.style.property;

import org.joml.Vector2i;
import org.joml.Vector2ic;

public class Position {

    public final Coordinate x;
    public final Coordinate y;

    private final Vector2i vector = new Vector2i();

    public Position(Coordinate x, Coordinate y) {
        this.x = x;
        this.y = y;
    }

    public void set(Position position) {
        this.x.set(position.x);
        this.y.set(position.y);
    }

    public Vector2ic asVector2i() {
        final int x = this.x.get();
        final int y = this.y.get();
        return this.vector.set(x, y);
    }

}

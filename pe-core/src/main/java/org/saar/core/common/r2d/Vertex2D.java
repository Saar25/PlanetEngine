package org.saar.core.common.r2d;

import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.saar.core.model.Vertex;

public interface Vertex2D extends Vertex {

    Vector2fc getPosition2f();

    Vector3fc getColour3f();

    static Vertex2D of(Vector2fc position, Vector3fc colour) {
        return new Vertex2D() {
            @Override public Vector2fc getPosition2f() { return position; }

            @Override public Vector3fc getColour3f() { return colour; }
        };
    }

}

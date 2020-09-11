package org.saar.core.common.r2d;

import org.joml.Vector2fc;
import org.joml.Vector3fc;

public class VertexBase2D implements Vertex2D {

    private final Vector2fc position;
    private final Vector3fc colour;

    public VertexBase2D(Vector2fc position, Vector3fc colour) {
        this.position = position;
        this.colour = colour;
    }

    @Override
    public Vector2fc getPosition2f() {
        return this.position;
    }

    @Override
    public Vector3fc getColour3f() {
        return this.colour;
    }
}

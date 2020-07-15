package org.saar.core.renderer.basic;

import org.joml.Vector2fc;
import org.joml.Vector3fc;

public class BasicVertex implements IBasicVertex {

    private final Vector2fc position;
    private final Vector3fc colour;

    public BasicVertex(Vector2fc position, Vector3fc colour) {
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

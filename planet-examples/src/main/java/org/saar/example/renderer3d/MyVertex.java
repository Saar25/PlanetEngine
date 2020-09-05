package org.saar.example.renderer3d;

import org.joml.Vector3fc;
import org.saar.core.common.r3d.Vertex3D;

public class MyVertex implements Vertex3D {

    private final Vector3fc position;
    private final Vector3fc colour;

    public MyVertex(Vector3fc position, Vector3fc colour) {
        this.position = position;
        this.colour = colour;
    }

    @Override
    public Vector3fc getPosition3f() {
        return this.position;
    }

    @Override
    public Vector3fc getColour3f() {
        return this.colour;
    }
}

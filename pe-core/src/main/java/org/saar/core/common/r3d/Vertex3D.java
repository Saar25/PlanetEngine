package org.saar.core.common.r3d;


import org.joml.Vector3fc;
import org.saar.core.model.Vertex;

public interface Vertex3D extends Vertex {

    Vector3fc getPosition3f();

    Vector3fc getNormal3f();

    Vector3fc getColour3f();

    static Vertex3D of(Vector3fc position, Vector3fc normal, Vector3fc colour) {
        return new Vertex3D() {
            @Override public Vector3fc getPosition3f() { return position; }

            @Override public Vector3fc getNormal3f() { return normal; }

            @Override public Vector3fc getColour3f() { return colour; }
        };
    }

}

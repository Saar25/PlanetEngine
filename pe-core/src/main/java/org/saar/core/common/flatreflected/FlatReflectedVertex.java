package org.saar.core.common.flatreflected;

import org.joml.Vector3fc;
import org.saar.core.model.Vertex;

public interface FlatReflectedVertex extends Vertex {

    Vector3fc getPosition3f();

    Vector3fc getNormal3f();

    static FlatReflectedVertex of(Vector3fc position, Vector3fc normal) {
        return new FlatReflectedVertex() {
            @Override public Vector3fc getPosition3f() { return position; }

            @Override public Vector3fc getNormal3f() { return normal; }
        };
    }

}

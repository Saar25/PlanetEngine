package org.saar.core.common.obj;

import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.saar.core.model.Vertex;

public interface ObjVertex extends Vertex {

    Vector3fc getPosition3f();

    Vector2fc getUvCoord2f();

    Vector3fc getNormal3f();

    static ObjVertex of(Vector3fc position, Vector2fc uvCoord, Vector3fc normal) {
        return new ObjVertex() {
            @Override public Vector3fc getPosition3f() { return position; }

            @Override public Vector2fc getUvCoord2f() { return uvCoord; }

            @Override public Vector3fc getNormal3f() { return normal; }
        };
    }

}

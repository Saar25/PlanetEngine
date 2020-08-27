package org.saar.core.common.obj;

import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.saar.core.model.Vertex;

public class ObjVertex implements Vertex {

    private final Vector3fc position;
    private final Vector2fc uvCoordinates;
    private final Vector3fc normal;

    public ObjVertex(Vector3fc position, Vector2fc uvCoordinates, Vector3fc normal) {
        this.position = position;
        this.uvCoordinates = uvCoordinates;
        this.normal = normal;
    }

    public Vector3fc getPosition3f() {
        return this.position;
    }

    public Vector2fc getUvCoordinates2f() {
        return this.uvCoordinates;
    }

    public Vector3fc getNormal3f() {
        return this.normal;
    }
}

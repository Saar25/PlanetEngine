package org.saar.example.obj;

import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.saar.core.common.obj.ObjVertexPrototype;

public class MyVertex implements ObjVertexPrototype {

    private final Vector3fc position;
    private final Vector2fc uvCoord;
    private final Vector3fc normal;

    public MyVertex(Vector3fc position, Vector2fc uvCoord, Vector3fc normal) {
        this.position = position;
        this.uvCoord = uvCoord;
        this.normal = normal;
    }

    @Override
    public Vector3fc getPosition3f() {
        return this.position;
    }

    @Override
    public Vector2fc getUvCoord2f() {
        return this.uvCoord;
    }

    @Override
    public Vector3fc getNormal3f() {
        return this.normal;
    }
}

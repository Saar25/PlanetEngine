package org.saar.core.renderer.obj;

import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.saar.core.model.Vertex;

public interface ObjVertexPrototype extends Vertex {

    Vector3fc getPosition3f();

    Vector2fc getUvCoord2f();

    Vector3fc getNormal3f();

}

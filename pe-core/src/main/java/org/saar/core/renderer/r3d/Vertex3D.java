package org.saar.core.renderer.r3d;

import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.saar.core.model.vertex.ModelVertex;

public interface Vertex3D extends ModelVertex {

    Vector2fc getPosition3f();

    Vector3fc getColour3f();

}

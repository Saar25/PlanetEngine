package org.saar.core.common.normalmap;

import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.saar.core.mesh.Vertex;

public interface NormalMappedVertex extends Vertex {

    Vector3fc getPosition3f();

    Vector2fc getUvCoord2f();

    Vector3fc getNormal3f();

    Vector3fc getTangent3f();

    Vector3fc getBiTangent3f();

}

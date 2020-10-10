package org.saar.core.common.flatreflected;

import org.joml.Vector3fc;
import org.saar.core.model.Vertex;

public interface FlatReflectedVertex extends Vertex {

    Vector3fc getPosition3f();

    Vector3fc getNormal3f();

}

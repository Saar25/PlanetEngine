package org.saar.core.common.smooth;


import org.joml.Vector3fc;
import org.saar.core.model.Vertex;

public interface SmoothVertex extends Vertex {

    Vector3fc getPosition3f();

    Vector3fc getNormal3f();

    Vector3fc getColour3f();

    Vector3fc getTarget3f();

}

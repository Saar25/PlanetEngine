package org.saar.core.common.r3d;


import org.joml.Vector3fc;
import org.saar.core.mesh.Vertex;

public interface Vertex3D extends Vertex {

    Vector3fc getPosition3f();

    Vector3fc getNormal3f();

    Vector3fc getColour3f();

}

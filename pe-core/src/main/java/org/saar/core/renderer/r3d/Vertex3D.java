package org.saar.core.renderer.r3d;

import org.joml.Vector3fc;
import org.saar.core.model.Vertex;

public interface Vertex3D extends Vertex {

    Vector3fc getPosition3f();

    Vector3fc getColour3f();

}

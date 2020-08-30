package org.saar.core.renderer.r2d;

import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.saar.core.model.Vertex;

public interface Vertex2D extends Vertex {

    Vector2fc getPosition2f();

    Vector3fc getColour3f();

}

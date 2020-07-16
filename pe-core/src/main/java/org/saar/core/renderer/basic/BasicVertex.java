package org.saar.core.renderer.basic;

import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.saar.core.model.vertex.ModelVertex;

public interface BasicVertex extends ModelVertex {

    Vector2fc getPosition2f();

    Vector3fc getColour3f();

}
